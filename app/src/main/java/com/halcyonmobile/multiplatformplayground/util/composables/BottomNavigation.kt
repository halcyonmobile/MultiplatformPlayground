package com.halcyonmobile.multiplatformplayground.util.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.halcyonmobile.multiplatformplayground.ui.theme.AppTheme
import dev.chrisbanes.accompanist.insets.navigationBarsPadding

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = 8.dp,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        color = backgroundColor,
        contentColor = contentColor,
        elevation = elevation,
        modifier = modifier
    ) {
        Surface(
            color = backgroundColor,
            contentColor = contentColor,
            modifier = Modifier.navigationBarsPadding()
        ) {
            Row(
                Modifier.fillMaxWidth()
                    .preferredHeight(AppTheme.dimens.bottomNavHeight),
                horizontalArrangement = Arrangement.SpaceBetween,
                content = content
            )
        }
    }
}