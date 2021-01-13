package com.halcyonmobile.multiplatformplayground.model.ui

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.model.ApplicationDetail

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
    val screenshots: List<ScreenshotUiModel>
)

fun ApplicationDetail.toApplicationDetailUiModel() = ApplicationDetailUiModel(
    id = id,
    name =name,
    developer = developer,
    icon = icon,
    rating = rating,
    ratingCount = ratingCount,
    storeUrl = storeUrl,
    description = description,
    downloads = downloads,
    version = version,
    size = size,
    favourite = favourite,
    categoryId = categoryId,
    screenshots = screenshots.map { it.toScreenshotUiModel() }
)

fun ApplicationDetailUiModel.toApplication() = Application(
    id = id,
    name = name,
    developer = developer,
    icon = icon,
    rating = rating,
    favourite = favourite,
    categoryId = categoryId
)