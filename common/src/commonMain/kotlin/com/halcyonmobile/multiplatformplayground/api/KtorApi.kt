package com.halcyonmobile.multiplatformplayground.api

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.takeFrom

internal abstract class KtorApi {
    protected val client = HttpClient {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }


    protected fun HttpRequestBuilder.apiUrl(path: String) {
//        header(HttpHeaders.Authorization, "token $TOKEN")
//        header(HttpHeaders.CacheControl, "no-cache")
        url {
            takeFrom(BASE_URL)
            encodedPath = path
            port = 8080
        }
    }

    companion object {
        // todo move to gradle
        const val BASE_URL = "localhost"
    }
}