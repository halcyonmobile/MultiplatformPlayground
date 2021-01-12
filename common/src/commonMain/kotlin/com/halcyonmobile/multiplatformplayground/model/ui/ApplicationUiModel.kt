package com.halcyonmobile.multiplatformplayground.model.ui

import com.halcyonmobile.multiplatformplayground.model.Application

sealed class ApplicationUiModel {

    abstract val id: Long // Added for easier List construction in SwiftUI

    data class App(
        override val id: Long,
        val name: String,
        val icon: String,
        val developer: String,
        val rating: Float,
        val favourite: Boolean = false,
        val categoryId: Long
    ) : ApplicationUiModel()

    object Loading : ApplicationUiModel() {
        override val id = 0L
    }
}

fun Application.toApplicationUiModel() =
    ApplicationUiModel.App(id, name, icon, developer, rating, favourite, categoryId)