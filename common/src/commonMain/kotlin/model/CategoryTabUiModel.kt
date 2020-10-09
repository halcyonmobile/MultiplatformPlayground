package model

import com.halcyonmobile.multiplatformplayground.model.Category

data class CategoryTabUiModel(val name: String, val icon: String, val isSelected: Boolean)

fun Category.toCategoryTabUiModel(isSelected: Boolean) = CategoryTabUiModel(name, icon, isSelected)