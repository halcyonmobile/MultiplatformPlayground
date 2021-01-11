package com.halcyonmobile.multiplatformplayground.model.ui

import com.halcyonmobile.multiplatformplayground.model.Screenshot

data class ScreenshotUiModel(
    val name: String,
    val image: String
)

fun Screenshot.toScreenshotUiModel() = ScreenshotUiModel(name, image)