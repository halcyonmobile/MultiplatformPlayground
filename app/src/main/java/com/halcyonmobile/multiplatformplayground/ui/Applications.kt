package com.halcyonmobile.multiplatformplayground.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onActive
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.halcyonmobile.multiplatformplayground.R
import com.halcyonmobile.multiplatformplayground.model.ui.ApplicationUiModel
import com.halcyonmobile.multiplatformplayground.ui.theme.AppTheme
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun Applications(
    items: List<ApplicationUiModel>,
    onApplicationClicked: (ApplicationUiModel.App) -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
    onBottomReached: () -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = contentPadding
    ) {
        itemsIndexed(items = items,
            itemContent = { index, item ->
                if (items.lastIndex == index) {
                    onActive {
                        onBottomReached()
                    }
                }
                when (item) {
                    is ApplicationUiModel.App -> Application(uiModel = item, onApplicationClicked)
                    ApplicationUiModel.Loading -> Box(
                        Modifier.fillParentMaxWidth(),
                        Alignment.Center
                    ) {
                        CircularProgressIndicator(Modifier.padding(16.dp))
                    }
                }
            })
    }
}

@Composable
private fun Application(
    uiModel: ApplicationUiModel.App,
    onClick: (ApplicationUiModel.App) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = { onClick(uiModel) })
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Surface(shape = RoundedCornerShape(8.dp)) {
            CoilImage(data = uiModel.icon, modifier = Modifier.size(64.dp))
        }
        Column(modifier = Modifier.fillMaxSize().align(Alignment.Top).padding(start = 16.dp)) {
            Text(text = uiModel.name, style = AppTheme.typography.body1)
            Text(text = uiModel.developer, style = AppTheme.typography.caption)
            Row(Modifier.wrapContentSize(Alignment.TopStart)) {
                Text(
                    text = uiModel.rating.toString(),
                    modifier = Modifier.align(Alignment.CenterVertically),
                    style = AppTheme.typography.caption
                )
                Image(
                    imageVector = vectorResource(id = R.drawable.ic_rating),
                    colorFilter = ColorFilter.tint(AppTheme.colors.secondary),
                    modifier = Modifier.size(16.dp).padding(start = 4.dp)
                )
            }
        }
    }
}
