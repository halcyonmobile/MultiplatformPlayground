package com.halcyonmobile.multiplatformplayground.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TabConstants.defaultTabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.halcyonmobile.multiplatformplayground.viewmodel.HomeViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.halcyonmobile.multiplatformplayground.R
import com.halcyonmobile.multiplatformplayground.model.ui.ApplicationUiModel
import dev.chrisbanes.accompanist.coil.CoilImage
import com.halcyonmobile.multiplatformplayground.model.ui.CategoryTabUiModel
import com.halcyonmobile.multiplatformplayground.ui.theme.AppTheme
import com.halcyonmobile.multiplatformplayground.viewmodel.ApplicationsViewModel
import dev.chrisbanes.accompanist.insets.AmbientWindowInsets
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding

@Composable
fun HomeScreen(
    onApplicationClicked: (ApplicationUiModel.App) -> Unit,
    onUploadApplication: (categoryId: Long) -> Unit
) {
    val viewModel = remember {
        HomeViewModel()
    }
    val categoryTabs by viewModel.categoryTabs.collectAsState(emptyList())
    val selectedCategory by viewModel.selectedCategory.collectAsState(null)
    val error by viewModel.error.collectAsState(null)

    Scaffold(floatingActionButton = {
        selectedCategory?.let {
            FloatingActionButton(
                onClick = { onUploadApplication(it.id) },
                modifier = Modifier
                    .padding(bottom = AppTheme.dimens.bottomNavHeight)
                    .navigationBarsPadding()
            ) { Icon(imageVector = vectorResource(id = R.drawable.ic_add)) }
        }
    }) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize().weight(1f)) {
                Tabs(categoryTabs = categoryTabs, onClick = viewModel::onTabClicked)
                selectedCategory?.let {
                    ApplicationsPerCategory(
                        categoryId = it.id,
                        onApplicationClicked = onApplicationClicked,
                        contentPadding = with(AmbientDensity.current) {
                            PaddingValues(
                                bottom = AppTheme.dimens.bottomNavHeight +
                                        AmbientWindowInsets.current.navigationBars.bottom.toDp()
                            )
                        }
                    )
                }
            }
            error?.let { Snackbar(text = { Text(text = it) }, modifier = Modifier.padding(16.dp)) }
        }
    }
}

@Composable
private fun ApplicationsPerCategory(
    categoryId: Long,
    onApplicationClicked: (ApplicationUiModel.App) -> Unit,
    contentPadding: PaddingValues = PaddingValues()
) {
    // TODO fix same viewModel instance issue https://github.com/InsertKoinIO/koin/issues/924
    val viewModel = remember(categoryId) {
        ApplicationsViewModel(categoryId)
    }
    val items by viewModel.items.collectAsState()
    val state by viewModel.state.collectAsState()

    when (state) {
        ApplicationsViewModel.State.EMPTY -> Box(Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                text = stringResource(id = R.string.applications_empty_message),
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center
            )
        }
        ApplicationsViewModel.State.ERROR -> Box(Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                text = stringResource(id = R.string.general_error),
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center
            )
        }
        ApplicationsViewModel.State.NORMAL -> Applications(
            items = items,
            onApplicationClicked = onApplicationClicked,
            contentPadding = contentPadding,
            viewModel::load
        )

    }
}

@Composable
fun Tabs(categoryTabs: List<CategoryTabUiModel>, onClick: (Int) -> Unit) {
    val selectedTabIndex = categoryTabs.indexOfFirst { it.isSelected }
    if (selectedTabIndex >= 0) {
        Surface(elevation = 8.dp) {
            ScrollableTabRow(
                selectedTabIndex,
                modifier = Modifier.statusBarsPadding().wrapContentWidth(),
                backgroundColor = MaterialTheme.colors.surface,
                indicator = { tabPositions ->
                    TabConstants.DefaultIndicator(
                        color = if (isSystemInDarkTheme()) MaterialTheme.colors.onSurface else MaterialTheme.colors.primary,
                        modifier = Modifier.defaultTabIndicatorOffset(tabPositions[selectedTabIndex])
                    )
                }
            ) {
                categoryTabs.forEachIndexed { index, categoryTab ->
                    CategoryTab(categoryTab) { onClick(index) }
                }
            }
        }
    }
}

@Composable
private fun CategoryTab(categoryTab: CategoryTabUiModel, onClick: () -> Unit) {
    Tab(selected = categoryTab.isSelected, onClick = onClick) {
        Row(Modifier.padding(12.dp)) {
            CoilImage(
                data = categoryTab.icon,
                modifier = Modifier.size(16.dp)
                    .align(Alignment.CenterVertically),
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = categoryTab.name, maxLines = 1)
        }
    }
}
