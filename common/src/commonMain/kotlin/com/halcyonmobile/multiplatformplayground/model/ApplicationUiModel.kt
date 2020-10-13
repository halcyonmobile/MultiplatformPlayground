package com.halcyonmobile.multiplatformplayground.model

sealed class ApplicationUiModel {

    class App(val data: Application) : ApplicationUiModel()
    object Loading : ApplicationUiModel()
}

fun Application.toApplicationUiModel() = ApplicationUiModel.App(this)