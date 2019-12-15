package com.halcyonmobile.multiplatformplayground.model

import com.halcyonmobile.multiplatformplayground.shared.util.CATEGORY_ICON
import com.halcyonmobile.multiplatformplayground.shared.util.CATEGORY_ID
import com.halcyonmobile.multiplatformplayground.shared.util.CATEGORY_NAME
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    @SerialName(CATEGORY_ID)
    val id: Long,
    @SerialName(CATEGORY_NAME)
    val name: String,
    @SerialName(CATEGORY_ICON)
    val icon: String
)