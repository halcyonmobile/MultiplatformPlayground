package com.halcyonmobile.multiplatformplayground.model.ui

import com.halcyonmobile.multiplatformplayground.model.ApplicationDetail
import com.halcyonmobile.multiplatformplayground.model.Screenshot

data class ApplicationDetailUiModel(
    val id: Long,
    val name: String,
    val developer: String,
    val icon: String,
    val rating: Float,
    val ratingCount: Int,
    val storeUrl: String,
    val description: String,
    val downloads: String,
    val version: String,
    val size: String,
    val favourite: Boolean = false,
    val categoryId: Long,
    val screenshots: List<Screenshot>
)

fun ApplicationDetail.toApplicationDetailUiModel() = ApplicationDetailUiModel(
    id,
    name,
    developer,
    icon,
    rating,
    ratingCount,
    storeUrl,
    description,
    downloads,
    version,
    size,
    favourite,
    categoryId,
    screenshots
)

fun ApplicationDetailUiModel.toApplicationDetail() = ApplicationDetail(
    id,
    name,
    developer,
    icon,
    rating,
    ratingCount,
    storeUrl,
    description,
    downloads,
    version,
    size,
    favourite,
    categoryId,
    screenshots
)