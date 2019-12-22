package com.halcyonmobile.multiplatformplayground.serialization

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.features.ContentConverter
import io.ktor.features.suitableCharset
import io.ktor.http.ContentType
import io.ktor.http.content.TextContent
import io.ktor.http.withCharset
import io.ktor.request.ApplicationReceiveRequest
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.pipeline.PipelineContext
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readRemaining
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

internal class KotlinxConverter : ContentConverter {
    @UseExperimental(KtorExperimentalAPI::class)
    override suspend fun convertForSend(
        context: PipelineContext<Any, ApplicationCall>,
        contentType: ContentType,
        value: Any
    ): Any? {
        @UseExperimental(ImplicitReflectionSerializer::class)
        val text = Json.nonstrict.stringify(value::class.serializer() as SerializationStrategy<Any>, value)
        return TextContent(text, contentType.withCharset(context.call.suitableCharset()))
    }

    @UseExperimental(UnstableDefault::class)
    override suspend fun convertForReceive(context: PipelineContext<ApplicationReceiveRequest, ApplicationCall>): Any? {
        val request = context.subject
        val channel = request.value as? ByteReadChannel ?: return null
        val type = request.type
        val text = channel.readRemaining().readText()
        @UseExperimental(ImplicitReflectionSerializer::class)
        return Json.nonstrict.parse(type.serializer(), text)
    }
}