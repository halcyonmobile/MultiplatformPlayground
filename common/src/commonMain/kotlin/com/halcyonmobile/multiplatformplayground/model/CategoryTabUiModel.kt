package com.halcyonmobile.multiplatformplayground.model

data class CategoryTabUiModel(
    val id: Long,
    val name: String,
    val icon: String,
    val isSelected: Boolean
)

fun Category.toCategoryTabUiModel(isSelected: Boolean) =
    CategoryTabUiModel(id, name, icon, isSelected)