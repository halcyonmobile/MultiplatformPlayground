package com.halcyonmobile.multiplatformplayground.model

import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable

@Serializable
data class Application(
    val id: Long,
    val name: String,
    val developer: String,
    @Optional
    val icon: String? = null,
    @Optional
    val rating: Float? = null,
    @Optional
    val ratingCount: Int? = null,
    @Optional
    val storeUrl: String? = null,
    @Optional
    val description: String? = null,
    @Optional
    val downloads: String? = null,
    @Optional
    val version: String? = null,
    @Optional
    val size: String? = null,
    @Optional
    val favourite: Boolean = false,
    @Optional
    var category: Category? = null,
    @Optional
    var screenshots: List<Screenshot>? = null

)