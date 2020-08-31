package com.halcyonmobile.multiplatformplayground.api

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.model.Screenshot
import com.halcyonmobile.multiplatformplayground.shared.util.installNetworkLogger
import io.ktor.client.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.*
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import kotlin.reflect.typeOf

abstract class KtorApi {
    @OptIn(ExperimentalStdlibApi::class)
    protected val client = HttpClient {
        installNetworkLogger()         // TODO investigate why this isn't working
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
        }
    }

    protected fun HttpRequestBuilder.json() {
        contentType(ContentType.Application.Json)
    }

    companion object {
        const val BASE_URL = "https://halcyon-multiplatform-backend.herokuapp.com/" //TODO: Move to Gradle
    }
}