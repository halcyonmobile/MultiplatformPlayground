package com.halcyonmobile.multiplatformplayground.ui.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRowFor
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.halcyonmobile.multiplatformplayground.R
import com.halcyonmobile.multiplatformplayground.shared.util.ImageFile
import com.halcyonmobile.multiplatformplayground.ui.theme.AppTheme
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun Screenshots(screenshots: List<ImageFile>, onAddScreenshot: () -> Unit = {}, showAdd: Boolean = false) {
    Column {
        val items = if(showAdd) screenshots + null else screenshots
        Text(
            text = stringResource(id = R.string.screenshots),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(16.dp)
        )
        LazyRowFor(items = items, modifier = Modifier.padding(8.dp)) {
            if (it != null) {
                CoilImage(
                    data = it.uri,
                    modifier = Modifier.preferredSize(88.dp).padding(8.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Card(
                    modifier = Modifier.preferredSize(88.dp).padding(8.dp),
                    shape = RectangleShape,
                    backgroundColor = AppTheme.colors.cardButton
                ) {
                    Box(Modifier.clickable(onClick = onAddScreenshot)) {
                        Image(
                            imageVector = vectorResource(id = R.drawable.ic_add_image),
                            colorFilter = ColorFilter.tint(AppTheme.colors.primary),
                            modifier = Modifier.wrapContentSize().align(Alignment.Center)
                        )
                    }

                }
            }
        }
    }
}