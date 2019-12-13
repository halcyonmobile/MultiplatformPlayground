package com.halcyonmobile.multiplatformplayground.api

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.model.Screenshot
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.takeFrom
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

abstract internal class KtorApi {
    protected val client = HttpClient {
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
        header(HttpHeaders.Authorization, "token $TOKEN")
        header(HttpHeaders.CacheControl, "no-cache")
        url {
            takeFrom(BASE_URL)
            encodedPath = path
        }
    }

    companion object {
        // todo move to gradle
        const val BASE_URL = "https://application-portfolio.herokuapp.com/api/v1/"
        const val TOKEN = "qwertyasdfghzxcvbn"
    }
}