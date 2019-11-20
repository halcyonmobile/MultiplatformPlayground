package com.halcyonmobile.multiplatformplayground.repository

import com.halcyonmobile.multiplatformplayground.api.ScreenshotApi
import com.halcyonmobile.multiplatformplayground.model.Screenshot

internal class ScreenshotRepository(private val screenshotApi: ScreenshotApi) {

    // todo update this stub
    suspend fun upload(screenshot: Screenshot): Screenshot = screenshot
}