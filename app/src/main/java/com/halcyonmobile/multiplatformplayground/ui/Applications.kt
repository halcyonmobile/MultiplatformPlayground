package com.halcyonmobile.multiplatformplayground.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.halcyonmobile.multiplatformplayground.R
import com.halcyonmobile.multiplatformplayground.model.ui.ApplicationUiModel
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun Applications(applications: List<ApplicationUiModel>, onApplicationClicked: (ApplicationUiModel.App) -> Unit) {
    // TODO add load more
    LazyColumnFor(
        items = applications,
        modifier = Modifier.fillMaxSize()
    ) { item ->
        when (item) {
            is ApplicationUiModel.App -> Application(uiModel = item, onApplicationClicked)
            ApplicationUiModel.Loading -> CircularProgressIndicator(
                Modifier.wrapContentSize(align = Alignment.Center).padding(16.dp)
            )
        }
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
//            CoilImage(data = uiModel.icon, modifier = Modifier.size(64.dp))
        }
        Column(modifier = Modifier.fillMaxSize().align(Alignment.Top).padding(start = 16.dp)) {
            Text(text = uiModel.name, style = MaterialTheme.typography.body1)
            Text(text = uiModel.developer, style = MaterialTheme.typography.caption)
            Row(Modifier.wrapContentSize(Alignment.TopStart)) {
                Text(
                    text = uiModel.rating.toString(),
                    modifier = Modifier.align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.caption
                )
                Image(
                    asset = vectorResource(id = R.drawable.ic_rating),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary),
                    modifier = Modifier.size(16.dp).padding(start = 4.dp)
                )
            }
        }
    }
}