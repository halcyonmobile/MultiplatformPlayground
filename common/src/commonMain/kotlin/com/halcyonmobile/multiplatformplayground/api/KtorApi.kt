package com.halcyonmobile.multiplatformplayground.api

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.model.Screenshot
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import kotlinx.serialization.json.Json

abstract class KtorApi {
    protected val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json.nonstrict).apply {
                setMapper(Application::class, Application.serializer())
                setMapper(Category::class, Category.serializer())
                setMapper(Screenshot::class, Screenshot.serializer())
            }
        }
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