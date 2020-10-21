package com.halcyonmobile.multiplatformplayground.model.ui

import com.halcyonmobile.multiplatformplayground.model.UploadApplicationModel
import com.halcyonmobile.multiplatformplayground.shared.util.File

data class UploadApplicationUiModel(
    val name: String = "",
    val developer: String = "",
    val icon: File? = null,
    val rating: Float? = null,
    val description: String = "",
    val downloads: String = "",
    val categoryId: Long,
    val screenshots: List<File> = emptyList()
)

fun UploadApplicationUiModel.toUploadApplicationModel() = UploadApplicationModel(
    name,
    developer,
    icon!!,
    rating!!,
    description,
    downloads,
    categoryId,
    screenshots
)

interface UploadApplicationUiModelChangeListener {
    fun onNameChanged(name: String)

    fun onDeveloperChanged(developer: String)

    fun onIconChanged(icon: File)

    fun onRatingChanged(rating: String)

    fun onDescriptionChanged(description: String)

    fun onDownloadsChanged(downloads: String)

    fun onCategoryChanged(categoryId: Long)
}