package com.halcyonmobile.multiplatformplayground.ui.theme.color

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val primary200 = Color(0xff73e8ff)
val primary500 = Color(0xff29b6f6)
val primary700 = Color(0xff0086c3)
val accent200 = Color(0xffffc400)
val accent700 = Color(0xffb28900)

val darkColors: AppColors = AppColors(
    primary = primary200,
    primaryVariant = primary700,
    secondary = accent200,
    secondaryVariant = accent200,
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
    primary = primary500,
    primaryVariant = primary700,
    secondary = accent200,
    secondaryVariant = accent700,
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