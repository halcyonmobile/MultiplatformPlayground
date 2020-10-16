package com.halcyonmobile.multiplatformplayground.model.ui

import com.halcyonmobile.multiplatformplayground.model.Category

data class CategoryTabUiModel(
    val id: Long,
    val name: String,
    val icon: String,
    val isSelected: Boolean
)

fun Category.toCategoryTabUiModel(isSelected: Boolean) =
    CategoryTabUiModel(id, name, icon, isSelected)