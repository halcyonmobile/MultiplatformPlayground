package com.halcyonmobile.multiplatformplayground.api

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.model.Screenshot
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import kotlin.reflect.typeOf

abstract class KtorApi {
    @OptIn(ExperimentalStdlibApi::class)
    protected val client = HttpClient(engine) {
        installNetworkLogger()
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
        }
    }

    protected fun HttpRequestBuilder.json() {
        contentType(ContentType.Application.Json)
    }

    companion object {
        const val BASE_URL = "http://localhost:8080/" //TODO: Move to Gradle
    }
}