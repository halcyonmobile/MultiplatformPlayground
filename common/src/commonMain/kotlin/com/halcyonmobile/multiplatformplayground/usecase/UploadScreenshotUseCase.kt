package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.model.Screenshot
import com.halcyonmobile.multiplatformplayground.repository.ScreenshotRepository

class UploadScreenshotUseCase internal constructor(private val screenshotRepository: ScreenshotRepository) {

    suspend operator fun invoke(screenshot: Screenshot) = screenshotRepository.upload(screenshot)
}