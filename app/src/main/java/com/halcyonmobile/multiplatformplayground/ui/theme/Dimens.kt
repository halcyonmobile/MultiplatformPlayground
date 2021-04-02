package com.halcyonmobile.multiplatformplayground.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal val localDimens = staticCompositionLocalOf { Dimens() }

data class Dimens(
    val bottomNavHeight: Dp = 56.dp
)