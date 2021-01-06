package com.halcyonmobile.multiplatformplayground.ui.theme.color

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val purple200 = Color(0xFFBB86FC)
val purple500 = Color(0xFF6200EE)
val purple700 = Color(0xFF3700B3)
val teal200 = Color(0xFF03DAC5)

val darkColors: AppColors = AppColors(
    primary = purple200,
    primaryVariant = purple700,
    secondary = teal200,
    secondaryVariant = Color(0xFF03DAC6),
    background = Color(0xFF121212),
    surface = Color(0xFF121212),
    error = Color(0xFFCF6679),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.Black,
    cardButton = Color.White.copy(alpha = 0.12f),
    false
)

val lightColors: AppColors = AppColors(
    primary = purple500,
    primaryVariant = purple700,
    secondary = teal200,
    secondaryVariant = Color(0xFF018786),
    background = Color.White,
    surface = Color.White,
    error = Color(0xFFB00020),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White,
    cardButton = Color.Black.copy(alpha = 0.12f),
    true
)


@Composable
val appColors
    get() = if (isSystemInDarkTheme()) {
        darkColors
    } else {
        lightColors
    }