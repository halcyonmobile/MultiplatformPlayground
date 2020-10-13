package com.halcyonmobile.multiplatformplayground.model

sealed class ApplicationUiModel {

    class App(
        val id: Long,
        val name: String,
        val icon: String,
        val developer: String,
        val favourite: Boolean = false,
        val categoryId: Long
    ) : ApplicationUiModel()

    object Loading : ApplicationUiModel()
}

fun Application.toApplicationUiModel() =
    ApplicationUiModel.App(id, name, icon, developer, favourite, categoryId)