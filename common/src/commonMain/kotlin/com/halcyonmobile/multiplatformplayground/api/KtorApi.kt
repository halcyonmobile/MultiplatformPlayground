package com.halcyonmobile.multiplatformplayground.api

import com.halcyonmobile.multiplatformplayground.shared.util.installNetworkLogger
import io.ktor.client.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.takeFrom

abstract class KtorApi {

    @OptIn(ExperimentalStdlibApi::class)
    protected val client = HttpClient {
        installNetworkLogger()
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    protected fun HttpRequestBuilder.apiUrl(path: String) {
        url {
            takeFrom(BASE_URL)
            encodedPath = path
        }
    }

    protected fun HttpRequestBuilder.json() {
        contentType(ContentType.Application.Json)
    }

    companion object {
        const val BASE_URL =
            "https://halcyon-multiplatform-backend.herokuapp.com/" //TODO: Move to Gradle
    }
}