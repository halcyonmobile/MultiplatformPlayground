package com.halcyonmobile.multiplatformplayground.model

import com.halcyonmobile.multiplatformplayground.shared.util.File

data class UploadApplicationModel(
    val name: String,
    val developer: String,
    val icon: File,
    val rating: Float,
    val description: String,
    val downloads: String,
    val categoryId: Long,
    val screenshots: List<File>
)