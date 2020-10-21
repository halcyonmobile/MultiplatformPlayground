package com.halcyonmobile.multiplatformplayground.model.ui

import com.halcyonmobile.multiplatformplayground.model.Category

data class CategoryUiModel(
    val id: Long,
    val name: String,
    val icon: String
)

fun Category.toCategoryUiModel() = CategoryUiModel(id, name, icon)