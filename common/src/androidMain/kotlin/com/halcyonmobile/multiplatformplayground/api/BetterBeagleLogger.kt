package com.halcyonmobile.multiplatformplayground.api

import com.pandulapeter.beagle.log.BeagleLogger
import com.pandulapeter.beagle.logKtor.BeagleKtorLogger
import io.ktor.client.HttpClient
import io.ktor.client.call.HttpClientCall
import io.ktor.client.features.HttpClientFeature
import io.ktor.client.features.observer.ResponseHandler
import io.ktor.client.features.observer.ResponseObserver
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.HttpSendPipeline
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.HttpResponsePipeline
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.Url
import io.ktor.http.charset
import io.ktor.http.content.OutgoingContent
import io.ktor.http.contentType
import io.ktor.http.fullPath
import io.ktor.util.AttributeKey
import io.ktor.utils.io.ByteChannel
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.ByteWriteChannel
import io.ktor.utils.io.charsets.Charset
import io.ktor.utils.io.close
import io.ktor.utils.io.core.readText
import io.ktor.utils.io.core.use
import io.ktor.utils.io.readRemaining
import io.ktor.utils.io.writeFully
import io.ktor.utils.io.writer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Modified version of https://ktor.io/clients/http-client/features/logging.html
 *
 * TODO: Move this to the Beagle library.
 */
class BetterBeagleLogger {

    private fun log(message: String) = BeagleLogger.log(message)

    private suspend fun logRequest(request: HttpRequestBuilder): OutgoingContent? {
        val content = request.body as OutgoingContent
        BeagleKtorLogger.logNetworkEvent(
            isOutgoing = true,
            url = "[${request.method.value}] ${Url(request.url)}",
            payload = "TODO", //TODO
            headers = mutableListOf<String>().apply {
                logHeaders(request.headers.entries(), content.headers) //TODO
            }
        )
        return logRequestBody(content)
    }

    private fun logResponse(response: HttpResponse) {
        BeagleKtorLogger.logNetworkEvent(
            isOutgoing = false,
            url = "[${response.call.request.method.value}] ${response.status.value} ${response.call.request.url.fullPath}",
            payload = "TODO", //TODO
            headers = mutableListOf<String>().apply {
                logHeaders(response.headers.entries()) //TODO
            }
        )
    }

    private fun logRequestException(context: HttpRequestBuilder, cause: Throwable) {
        BeagleKtorLogger.logNetworkEvent(
            isOutgoing = true,
            url = "[${context.method.value}] FAIL ${Url(context.url)}",
            payload = cause.message ?: "HTTP Failed",
            headers = null //TODO
        )
    }

    private fun logResponseException(context: HttpClientCall, cause: Throwable) {
        BeagleKtorLogger.logNetworkEvent(
            isOutgoing = false,
            url = "[${context.request.method.value}] FAIL ${context.request.url.fullPath}",
            payload = cause.message ?: "HTTP Failed"
        )
    }

    private fun logHeaders(
        requestHeaders: Set<Map.Entry<String, List<String>>>,
        contentHeaders: Headers? = null
    ) {
        log("HEADERS")
        requestHeaders.forEach { (key, values) ->
            log("-> $key: ${values.joinToString("; ")}")
        }
        contentHeaders?.forEach { key, values ->
            log("-> $key: ${values.joinToString("; ")}")
        }
    }

    private suspend fun logResponseBody(contentType: ContentType?, content: ByteReadChannel) {
        log("BODY Content-Type: $contentType")
        log("BODY START")
        val message = content.readText(contentType?.charset() ?: Charsets.UTF_8)
        log(message)
        log("BODY END")
    }

    private suspend fun logRequestBody(content: OutgoingContent): OutgoingContent? {
        log("BODY Content-Type: ${content.contentType}")

        val charset = content.contentType?.charset() ?: Charsets.UTF_8

        val channel = ByteChannel()
        GlobalScope.launch(Dispatchers.Unconfined) {
            val text = channel.readText(charset)
            log("BODY START")
            log(text)
            log("BODY END")
        }

        return content.observe(channel)
    }

    private suspend fun OutgoingContent.observe(log: ByteWriteChannel): OutgoingContent = when (this) {
        is OutgoingContent.ByteArrayContent -> {
            log.writeFully(bytes())
            log.close()
            this
        }
        is OutgoingContent.ReadChannelContent -> {
            val responseChannel = ByteChannel()
            val content = readFrom()

            content.copyToBoth(log, responseChannel)
            LoggingContent(responseChannel)
        }
        is OutgoingContent.WriteChannelContent -> {
            val responseChannel = ByteChannel()
            val content = toReadChannel()
            content.copyToBoth(log, responseChannel)
            LoggingContent(responseChannel)
        }
        else -> {
            log.close()
            this
        }
    }

    private fun OutgoingContent.WriteChannelContent.toReadChannel(
    ): ByteReadChannel = GlobalScope.writer(Dispatchers.Unconfined) {
        writeTo(channel)
    }.channel

    private suspend inline fun ByteReadChannel.readText(charset: Charset): String = readRemaining().readText(charset = charset)

    private fun ByteReadChannel.copyToBoth(first: ByteWriteChannel, second: ByteWriteChannel) {
        GlobalScope.launch(Dispatchers.Unconfined) {
            try {
                while (!isClosedForRead && (!first.isClosedForWrite || !second.isClosedForWrite)) {
                    readRemaining(CHUNK_BUFFER_SIZE).use {
                        try {
                            first.writePacket(it.copy())
                            second.writePacket(it.copy())
                        } catch (cause: Throwable) {
                            this@copyToBoth.cancel(cause)
                            first.close(cause)
                            second.close(cause)
                        }
                    }
                }
            } catch (cause: Throwable) {
                first.close(cause)
                second.close(cause)
            } finally {
                first.close()
                second.close()
            }
        }
    }

    internal class LoggingContent(private val channel: ByteReadChannel) : OutgoingContent.ReadChannelContent() {
        override fun readFrom(): ByteReadChannel = channel
    }


    companion object : HttpClientFeature<Nothing, BetterBeagleLogger> {
        private const val CHUNK_BUFFER_SIZE = 4096L

        override val key: AttributeKey<BetterBeagleLogger> = AttributeKey("ClientLogging")

        override fun prepare(block: Nothing.() -> Unit) = BetterBeagleLogger()

        override fun install(feature: BetterBeagleLogger, scope: HttpClient) {
            scope.sendPipeline.intercept(HttpSendPipeline.Monitoring) {
                val response = try {
                    feature.logRequest(context)
                } catch (_: Throwable) {
                    null
                } ?: subject

                try {
                    proceedWith(response)
                } catch (cause: Throwable) {
                    feature.logRequestException(context, cause)
                    throw cause
                }
            }

            scope.responsePipeline.intercept(HttpResponsePipeline.Receive) {
                try {
                    feature.logResponse(context.response)
                    proceedWith(subject)
                } catch (cause: Throwable) {
                    feature.logResponseException(context, cause)
                    throw cause
                }
            }

            val observer: ResponseHandler = {
                try {
                    feature.logResponseBody(it.contentType(), it.content)
                } catch (_: Throwable) {
                }
            }
            ResponseObserver.install(ResponseObserver(observer), scope)
        }
    }
}