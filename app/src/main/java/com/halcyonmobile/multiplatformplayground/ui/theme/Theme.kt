package com.halcyonmobile.multiplatformplayground.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.halcyonmobile.multiplatformplayground.ui.theme.color.localColors
import com.halcyonmobile.multiplatformplayground.ui.theme.color.AppColors
import com.halcyonmobile.multiplatformplayground.ui.theme.color.appColors
import com.halcyonmobile.multiplatformplayground.ui.theme.color.materialColors

object AppTheme {
    val dimens: Dimens
        @ReadOnlyComposable
        @Composable
        get() = localDimens.current

    val colors: AppColors
        @ReadOnlyComposable
        @Composable
        get() = localColors.current

    val typography: Typography
        @ReadOnlyComposable
        @Composable
        get() = MaterialTheme.typography

    val shapes: Shapes
        @ReadOnlyComposable
        @Composable
        get() = MaterialTheme.shapes
}

@Composable
fun MultiplatformPlaygroundTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(localColors provides appColors) {
        MaterialTheme(
            colors = AppTheme.colors.materialColors,
            typography = AppTheme.typography,
            shapes = AppTheme.shapes,
            content = content
        )
    }
}