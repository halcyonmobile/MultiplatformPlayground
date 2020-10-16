package com.halcyonmobile.multiplatformplayground.ui

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Snackbar
import androidx.compose.material.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.halcyonmobile.multiplatformplayground.viewmodel.HomeViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.halcyonmobile.multiplatformplayground.model.ui.ApplicationUiModel
import dev.chrisbanes.accompanist.coil.CoilImage
import com.halcyonmobile.multiplatformplayground.model.ui.CategoryTabUiModel
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(onApplicationClicked: (ApplicationUiModel.App) -> Unit) {
    val viewModel = getViewModel<HomeViewModel>()
    val categoryTabs by viewModel.categoryTabs.collectAsState(emptyList())
    val selectedCategory by viewModel.selectedCategory.collectAsState(null)
    val error by viewModel.error.collectAsState(null)

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().weight(1f)) {
            Tabs(categoryTabs = categoryTabs, onClick = viewModel::onTabClicked)
            selectedCategory?.let {
                Applications(categoryId = it.id, onApplicationClicked = onApplicationClicked)
            }
        }
        error?.let { Snackbar(text = { Text(text = it) }, modifier = Modifier.padding(16.dp)) }
    }
}

@Composable
fun Tabs(categoryTabs: List<CategoryTabUiModel>, onClick: (Int) -> Unit) {
    val selectedTabIndex = categoryTabs.indexOfFirst { it.isSelected }
    if (selectedTabIndex >= 0) {
        ScrollableTabRow(selectedTabIndex, modifier = Modifier.wrapContentWidth()) {
            categoryTabs.forEachIndexed { index, categoryTab ->
                Tab(selected = categoryTab.isSelected, onClick = { onClick(index) }) {
                    Row(Modifier.padding(8.dp)) {
                        Text(text = categoryTab.name, maxLines = 1)
                        Spacer(modifier = Modifier.size(8.dp))
                        CoilImage(
                            data = categoryTab.icon,
                            modifier = Modifier.size(16.dp).align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    }
}
