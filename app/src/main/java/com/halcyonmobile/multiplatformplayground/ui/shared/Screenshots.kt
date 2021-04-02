package com.halcyonmobile.multiplatformplayground.ui.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.CoilImage
import com.halcyonmobile.multiplatformplayground.R
import com.halcyonmobile.multiplatformplayground.shared.util.ImageFile
import com.halcyonmobile.multiplatformplayground.ui.theme.AppTheme

@Composable
fun Screenshots(
    screenshots: List<ImageFile>,
    onAddScreenshot: () -> Unit = {},
    showAdd: Boolean = false
) {
    Column {
        val items = if (showAdd) screenshots + null else screenshots
        Text(
            text = stringResource(id = R.string.screenshots),
            style = AppTheme.typography.h6,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        LazyRow(modifier = Modifier.padding(vertical = 8.dp)) {
            items(items = items, itemContent = { item ->
                if (item != null) {
                    CoilImage(
                        data = item.uri,
                        modifier = Modifier.size(88.dp).padding(8.dp),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                } else {
                    Card(
                        modifier = Modifier.size(88.dp).padding(8.dp),
                        shape = RectangleShape,
                        backgroundColor = AppTheme.colors.cardButton
                    ) {
                        Box(Modifier.clickable(onClick = onAddScreenshot)) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_add_image),
                                colorFilter = ColorFilter.tint(AppTheme.colors.primary),
                                modifier = Modifier.wrapContentSize().align(Alignment.Center),
                                contentDescription = null
                            )
                        }

                    }
                }
            })
        }
    }
}