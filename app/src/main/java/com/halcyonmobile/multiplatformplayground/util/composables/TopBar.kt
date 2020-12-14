package com.halcyonmobile.multiplatformplayground.util.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.halcyonmobile.multiplatformplayground.R
import dev.chrisbanes.accompanist.insets.statusBarsPadding


@Composable
fun BackBar(upPress: () -> Unit) {
    Surface(elevation = 8.dp) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                imageVector = vectorResource(id = R.drawable.ic_back),
                modifier = Modifier.clickable(onClick = upPress)
                    .statusBarsPadding()
                    .padding(16.dp)
            )
        }
    }
}