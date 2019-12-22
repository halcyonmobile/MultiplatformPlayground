package com.halcyonmobile.multiplatformplayground.model

import com.halcyonmobile.multiplatformplayground.shared.util.SCREENSHOT_ID
import com.halcyonmobile.multiplatformplayground.shared.util.SCREENSHOT_IMAGE
import com.halcyonmobile.multiplatformplayground.shared.util.SCREENSHOT_NAME
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Screenshot(
    @SerialName(SCREENSHOT_ID)
    val id: Long = 0,
    @SerialName(SCREENSHOT_NAME)
    val name: String,
    @SerialName(SCREENSHOT_IMAGE)
    val image: String
)