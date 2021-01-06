package com.halcyonmobile.multiplatformplayground.ui.theme

import androidx.compose.runtime.staticAmbientOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal val AmbientDimens = staticAmbientOf { Dimens() }

data class Dimens(
    val bottomNavHeight: Dp = 56.dp
)