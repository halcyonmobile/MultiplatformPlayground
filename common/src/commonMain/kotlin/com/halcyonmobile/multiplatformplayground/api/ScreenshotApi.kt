package com.halcyonmobile.multiplatformplayground.api

import com.halcyonmobile.multiplatformplayground.shared.util.File
import io.ktor.client.request.post

internal class ScreenshotApi(private val ktorApi: KtorApi) : KtorApi by ktorApi {

    suspend fun uploadScreenshot(screenshot: File): Unit = client.post {
        json()
        apiUrl("/screenshots")
    }
}