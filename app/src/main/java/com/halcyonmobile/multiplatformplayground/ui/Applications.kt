package com.halcyonmobile.multiplatformplayground.ui

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.halcyonmobile.multiplatformplayground.model.ApplicationUiModel
import com.halcyonmobile.multiplatformplayground.viewmodel.ApplicationsViewModel
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun Applications(categoryId: Long) {
    val viewModel = getViewModel<ApplicationsViewModel> { parametersOf(categoryId) }
    val applications by viewModel.applications.collectAsState()

    LazyColumnFor(
        items = applications,
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
    ) { item ->
        when (item) {
            is ApplicationUiModel.App -> Application(uiModel = item) {}
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
        modifier = Modifier.fillMaxWidth().clickable(
            onClick = { onClick(uiModel) }
        ).padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Surface(shape = RoundedCornerShape(8.dp)) {
            CoilImage(data = uiModel.icon, modifier = Modifier.size(64.dp))
        }
        Column(modifier = Modifier.fillMaxSize().align(Alignment.Top).padding(start = 16.dp)) {
            Text(text = uiModel.name, style = MaterialTheme.typography.body1)
            Text(text = uiModel.developer, style = MaterialTheme.typography.caption)
        }
    }
}

@Preview(name = "Applications")
@Composable
fun ApplicationsPreview() {
    Applications(categoryId = 0)
}
