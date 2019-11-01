package com.nagyrobi.multiplatformplayground.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Long,
    val name: String,
    val icon: String
)