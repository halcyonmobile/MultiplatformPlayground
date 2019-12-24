package com.halcyonmobile.multiplatformplayground.api

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.model.Screenshot
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.takeFrom
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

internal abstract class KtorApi {
    protected val client = HttpClient {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json.nonstrict).apply {
                registerMapper(Application.serializer())
                registerMapper(Category.serializer())
                registerMapper(Screenshot.serializer())
            }
        }
    }

    private inline fun <reified T : Any> KotlinxSerializer.registerMapper(mapper: KSerializer<T>) {
        register(mapper)
        registerList(mapper)
    }

    protected fun HttpRequestBuilder.apiUrl(path: String) {
//        header(HttpHeaders.Authorization, "token $TOKEN")
        header(HttpHeaders.CacheControl, "no-cache")
        url {
            takeFrom(BASE_URL)
            encodedPath = path
        }
    }

    companion object {
        // todo move to gradle
        const val BASE_URL = "localhost:8080/"
//        const val TOKEN = "qwertyasdfghzxcvbn"
    }
}