package com.halcyonmobile.multiplatformplayground.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableContract
import androidx.compose.runtime.Providers
import com.halcyonmobile.multiplatformplayground.ui.theme.color.AmbientColors
import com.halcyonmobile.multiplatformplayground.ui.theme.color.AppColors
import com.halcyonmobile.multiplatformplayground.ui.theme.color.appColors
import com.halcyonmobile.multiplatformplayground.ui.theme.color.materialColors

object AppTheme {
    @Composable
    @ComposableContract(readonly = true)
    val dimens: Dimens
        get() = AmbientDimens.current

    @Composable
    @ComposableContract(readonly = true)
    val colors: AppColors
        get() = AmbientColors.current

    @Composable
    @ComposableContract(readonly = true)
    val typography: Typography
        get() = MaterialTheme.typography

    @Composable
    @ComposableContract(readonly = true)
    val shapes: Shapes
        get() = MaterialTheme.shapes
}

@Composable
fun MultiplatformPlaygroundTheme(content: @Composable () -> Unit) {
    Providers(AmbientColors provides appColors) {
        MaterialTheme(
            colors = AppTheme.colors.materialColors,
            typography = AppTheme.typography,
            shapes = AppTheme.shapes,
            content = content
        )
    }
}