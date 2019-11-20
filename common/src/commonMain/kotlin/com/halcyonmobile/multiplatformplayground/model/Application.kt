package com.halcyonmobile.multiplatformplayground.model

import com.halcyonmobile.multiplatformplayground.shared.util.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Application(
    @SerialName(APP_ID)
    val id: Long,
    @SerialName(APP_NAME)
    val name: String,
    @SerialName(APP_DEVELOPER)
    val developer: String,
    @SerialName(APP_ICON)
    val icon: String? = null,
    @SerialName(APP_RATING)
    val rating: Float? = null,
    @SerialName(APP_RATING_COUNT)
    val ratingCount: Int? = null,
    @SerialName(APP_STORE_URL)
    val storeUrl: String? = null,
    @SerialName(APP_DESCRIPTION)
    val description: String? = null,
    @SerialName(APP_DOWNLOADS)
    val downloads: String? = null,
    @SerialName(APP_VERSION)
    val version: String? = null,
    @SerialName(APP_SIZE)
    val size: String? = null,
    @SerialName(APP_FAVOURITE)
    val favourite: Boolean = false,
    @SerialName(APP_CATEGORY)
    var category: Category? = null,
    @SerialName(APP_SCREENSHOTS)
    var screenshots: List<Screenshot>? = null
)