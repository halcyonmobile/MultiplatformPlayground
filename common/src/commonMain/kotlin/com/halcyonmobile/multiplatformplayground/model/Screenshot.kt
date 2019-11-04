package com.halcyonmobile.multiplatformplayground.model

import kotlinx.serialization.Serializable

@Serializable
data class Screenshot(
    val id: Long = 0,
    val name: String?,
    val image: String
)