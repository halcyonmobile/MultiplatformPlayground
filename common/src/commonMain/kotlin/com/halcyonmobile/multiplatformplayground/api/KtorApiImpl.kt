package com.halcyonmobile.multiplatformplayground.api

import com.halcyonmobile.multiplatformplayground.shared.util.installNetworkLogger
import io.ktor.client.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import com.halcyonmobile.multiplatformplayground.BuildKonfig

object KtorApiImpl : KtorApi {

    val baseUrl = BuildKonfig.baseUrl

    @OptIn(ExperimentalStdlibApi::class)
    override val client = HttpClient {
        installNetworkLogger()
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