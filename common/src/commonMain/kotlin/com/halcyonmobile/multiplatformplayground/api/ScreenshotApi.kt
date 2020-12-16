package com.halcyonmobile.multiplatformplayground.api

import com.halcyonmobile.multiplatformplayground.shared.util.ImageFile
import io.ktor.client.request.post

internal class ScreenshotApi(private val ktorApi: KtorApi) : KtorApi by ktorApi {

    suspend fun uploadScreenshot(screenshot: ImageFile): Unit = client.post {
        json()
        apiUrl("/screenshots")
    }
}