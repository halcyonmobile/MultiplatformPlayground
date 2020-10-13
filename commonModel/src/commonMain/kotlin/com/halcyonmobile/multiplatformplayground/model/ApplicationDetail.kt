package com.halcyonmobile.multiplatformplayground.model

import com.halcyonmobile.multiplatformplayground.shared.util.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApplicationDetail(
    @SerialName(APP_ICON)
    val icon: String,
    @SerialName(APP_RATING)
    val rating: Float,
    @SerialName(APP_RATING_COUNT)
    val ratingCount: Int,
    @SerialName(APP_STORE_URL)
    val storeUrl: String,
    @SerialName(APP_DESCRIPTION)
    val description: String,
    @SerialName(APP_DOWNLOADS)
    val downloads: String,
    @SerialName(APP_VERSION)
    val version: String,
    @SerialName(APP_SIZE)
    val size: String,
    @SerialName(APP_SCREENSHOTS)
    val screenshots: List<Screenshot>
)