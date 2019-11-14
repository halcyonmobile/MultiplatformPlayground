package com.halcyonmobile.multiplatformplayground.model

import kotlinx.serialization.Serializable

@Serializable
data class Application(
    val id: Long,
    val name: String,
    val developer: String,
    val icon: String? = null,
    val rating: Float? = null,
    val ratingCount: Int? = null,
    val storeUrl: String? = null,
    val description: String? = null,
    val downloads: String? = null,
    val version: String? = null,
    val size: String? = null,
    val favourite: Boolean = false,
    var category: Category? = null,
    var screenshots: List<Screenshot>? = null

)