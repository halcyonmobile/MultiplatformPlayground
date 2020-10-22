package com.halcyonmobile.multiplatformplayground.ui

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.halcyonmobile.multiplatformplayground.viewmodel.HomeViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.halcyonmobile.multiplatformplayground.R
import com.halcyonmobile.multiplatformplayground.model.ui.ApplicationUiModel
import dev.chrisbanes.accompanist.coil.CoilImage
import com.halcyonmobile.multiplatformplayground.model.ui.CategoryTabUiModel
import com.halcyonmobile.multiplatformplayground.viewmodel.ApplicationsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun HomeScreen(
    onApplicationClicked: (ApplicationUiModel.App) -> Unit,
    onUploadApplication: (categoryId: Long) -> Unit
) {
    val viewModel = getViewModel<HomeViewModel>()
    val categoryTabs by viewModel.categoryTabs.collectAsState(emptyList())
    val selectedCategory by viewModel.selectedCategory.collectAsState(null)
    val error by viewModel.error.collectAsState(null)

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = { onUploadApplication(selectedCategory!!.id) },
            icon = { Icon(asset = vectorResource(id = R.drawable.ic_add)) },
            modifier = Modifier.padding(16.dp)
        )
    }, bodyContent = {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize().weight(1f)) {
                Tabs(categoryTabs = categoryTabs, onClick = viewModel::onTabClicked)
                selectedCategory?.let {
                    ApplicationsPerCategory(
                        categoryId = it.id,
                        onApplicationClicked = onApplicationClicked
                    )
                }
            }
            error?.let { Snackbar(text = { Text(text = it) }, modifier = Modifier.padding(16.dp)) }
        }
    })
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
private fun ApplicationsPerCategory(
    categoryId: Long,
    onApplicationClicked: (ApplicationUiModel.App) -> Unit
) {
    // TODO fix same viewModel instance issue https://github.com/InsertKoinIO/koin/issues/924
    val viewModel = getViewModel<ApplicationsViewModel> { parametersOf(categoryId) }
    val applications by viewModel.applications.collectAsState()

    Applications(applications = applications, onApplicationClicked = onApplicationClicked)
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
