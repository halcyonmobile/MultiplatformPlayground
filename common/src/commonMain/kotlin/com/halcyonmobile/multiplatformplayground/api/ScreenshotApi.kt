package com.halcyonmobile.multiplatformplayground.api

import com.halcyonmobile.multiplatformplayground.shared.util.File
import io.ktor.client.request.post

internal class ScreenshotApi : KtorApi() {

    suspend fun uploadScreenshot(screenshot: File): Unit = client.post {
        apiUrl("/screenshots")
    }
}