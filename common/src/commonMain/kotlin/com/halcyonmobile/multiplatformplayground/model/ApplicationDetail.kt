package com.halcyonmobile.multiplatformplayground.model

data class ApplicationDetail(
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

fun ApplicationDetailResponse.toApplicationDetail() =
    ApplicationDetail(
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