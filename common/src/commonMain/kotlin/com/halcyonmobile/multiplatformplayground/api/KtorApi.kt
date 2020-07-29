package com.halcyonmobile.multiplatformplayground.api

import com.halcyonmobile.multiplatformplayground.model.*
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.*
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import kotlinx.serialization.list
import kotlin.reflect.typeOf

internal abstract class KtorApi {
    @OptIn(ExperimentalStdlibApi::class)
    protected val client = HttpClient {
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer().apply {
                // TODO check out this, might not be needed anymore
                typeOf<Application>()
                typeOf<Category>()
                typeOf<Screenshot>()
            }
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

    protected fun HttpRequestBuilder.json() {
        contentType(ContentType.Application.Json)
    }

    companion object {
        // todo move to gradle
        const val BASE_URL = "localhost"
    }
}