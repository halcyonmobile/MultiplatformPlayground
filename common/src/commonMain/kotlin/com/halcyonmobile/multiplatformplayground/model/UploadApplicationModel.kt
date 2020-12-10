package com.halcyonmobile.multiplatformplayground.model

import com.halcyonmobile.multiplatformplayground.shared.util.ImageFile

data class UploadApplicationModel(
    val name: String,
    val developer: String,
    val icon: ImageFile,
    val rating: Float,
    val description: String,
    val downloads: String,
    val categoryId: Long,
    val screenshots: List<ImageFile>
)