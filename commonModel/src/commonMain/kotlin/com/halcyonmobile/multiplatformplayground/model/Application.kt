package com.halcyonmobile.multiplatformplayground.model

import com.halcyonmobile.multiplatformplayground.shared.util.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class ApplicationWithDetail(
    val application: Application,
    val applicationDetail: ApplicationDetail
)

@Serializable
data class Application(
    @SerialName(APP_ID)
    val id: Long = 0,
    @SerialName(APP_NAME)
    val name: String,
    @SerialName(APP_DEVELOPER)
    val developer: String,
    @SerialName(APP_FAVOURITE)
    val favourite: Boolean = false,
    @SerialName(APP_CATEGORY)
    var category: Category? = null
)

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
    var screenshots: List<Screenshot>
)

fun ApplicationDetailResponse.toApplicationWithDetail() = ApplicationWithDetail(
    application = Application(id, name, developer, favourite, category),
    applicationDetail = ApplicationDetail(
        icon,
        rating,
        ratingCount,
        storeUrl,
        description,
        downloads,
        version,
        size,
        screenshots
    )
)