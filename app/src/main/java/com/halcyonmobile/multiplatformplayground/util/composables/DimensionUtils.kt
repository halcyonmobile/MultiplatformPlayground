package com.halcyonmobile.multiplatformplayground.util.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.AmbientDensity
import androidx.compose.ui.unit.dp

@Composable
@Stable
fun Int.toDp() = (this / AmbientDensity.current.density).dp