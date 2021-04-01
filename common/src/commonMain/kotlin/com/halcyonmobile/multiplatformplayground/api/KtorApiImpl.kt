package com.halcyonmobile.multiplatformplayground.api

import com.halcyonmobile.multiplatformplayground.shared.util.installNetworkLogger
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import com.halcyonmobile.multiplatformplayground.BuildKonfig
import io.ktor.client.HttpClient
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.SIMPLE

object KtorApiImpl : KtorApi {

    val baseUrl = BuildKonfig.baseUrl

    override val client = HttpClient {
//        Enabling logger causes Multipart requests to suspend endlessly
//        installNetworkLogger()
//        install(Logging) {
//            logger = Logger.SIMPLE
//            level = LogLevel.ALL
//        }
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    override fun HttpRequestBuilder.apiUrl(path: String) {
        url {
            takeFrom(baseUrl)
            encodedPath = path
        }
    }

    override fun HttpRequestBuilder.json() {
        contentType(ContentType.Application.Json)
    }
}