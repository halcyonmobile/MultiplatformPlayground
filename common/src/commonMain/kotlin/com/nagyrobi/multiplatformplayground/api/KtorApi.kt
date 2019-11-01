package com.nagyrobi.multiplatformplayground.api

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.serialization.json.Json

abstract class KtorApi() {
    protected val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json.nonstrict).apply {
                // todo set Mappers
            }
        }
    }

    companion object {
        const val BASE_URL = ""
    }
}