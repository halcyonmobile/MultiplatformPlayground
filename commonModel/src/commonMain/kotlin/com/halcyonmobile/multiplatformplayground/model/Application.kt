package com.halcyonmobile.multiplatformplayground.model

import com.halcyonmobile.multiplatformplayground.shared.util.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Application(
    @SerialName(APP_ID)
    val id: Long = 0,
    @SerialName(APP_NAME)
    val name: String,
    @SerialName(APP_ICON)
    val icon: String,
    @SerialName(APP_DEVELOPER)
    val developer: String,
    @SerialName(APP_FAVOURITE)
    val favourite: Boolean = false,
    @SerialName(APP_CATEGORY_ID)
    val categoryId: Long
)