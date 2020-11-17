package com.halcyonmobile.multiplatformplayground.api

import com.halcyonmobile.multiplatformplayground.shared.util.installNetworkLogger
import io.ktor.client.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.takeFrom

object KtorApiImpl : KtorApi {

    @OptIn(ExperimentalStdlibApi::class)
    override val client = HttpClient {
        installNetworkLogger()
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    override fun HttpRequestBuilder.apiUrl(path: String) {
        url {
            takeFrom(BASE_URL)
            encodedPath = path
        }
    }

    override fun HttpRequestBuilder.json() {
        contentType(ContentType.Application.Json)
    }

    const val BASE_URL =
        "https://halcyon-multiplatform-backend.herokuapp.com/" //TODO: Move to Gradle
}