package com.halcyonmobile.multiplatformplayground.model

data class ApplicationDetail(
    val ratingCount: Int,
    val storeUrl: String,
    val description: String,
    val downloads: String,
    val version: String,
    val size: String,
    val screenshots: List<Screenshot>
)