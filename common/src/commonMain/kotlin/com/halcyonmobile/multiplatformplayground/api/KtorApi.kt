package com.halcyonmobile.multiplatformplayground.api

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.model.Screenshot
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.SIMPLE
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import kotlin.reflect.typeOf

internal abstract class KtorApi {
    @OptIn(ExperimentalStdlibApi::class)
    protected val client = HttpClient(engine) {
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
        install(Logging) {
            //TODO: logger = BeagleKtorLogger
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