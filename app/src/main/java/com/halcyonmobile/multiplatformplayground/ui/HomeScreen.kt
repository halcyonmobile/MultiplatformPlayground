package com.halcyonmobile.multiplatformplayground.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.CoilImage
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.halcyonmobile.multiplatformplayground.R
import com.halcyonmobile.multiplatformplayground.model.ui.ApplicationUiModel
import com.halcyonmobile.multiplatformplayground.model.ui.CategoryTabUiModel
import com.halcyonmobile.multiplatformplayground.ui.theme.AppTheme
import com.halcyonmobile.multiplatformplayground.viewmodel.ApplicationsViewModel
import com.halcyonmobile.multiplatformplayground.viewmodel.HomeViewModel

@Composable
fun HomeScreen(onApplicationClicked: (ApplicationUiModel.App) -> Unit) {
    val viewModel = remember {
        HomeViewModel()
    }
    val categoryTabs by viewModel.categoryTabs.collectAsState(emptyList())
    val selectedCategory by viewModel.selectedCategory.collectAsState(null)
    val state by viewModel.state.collectAsState(HomeViewModel.State.LOADING)

    when (state) {
        HomeViewModel.State.LOADING -> Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
        }
        HomeViewModel.State.NORMAL -> Scaffold() {
            Column(modifier = Modifier.fillMaxSize()) {
                Tabs(categoryTabs = categoryTabs, onClick = viewModel::onTabClicked)
                selectedCategory?.let {
                    ApplicationsPerCategory(
                        categoryId = it.id,
                        onApplicationClicked = onApplicationClicked,
                        contentPadding = with(LocalDensity.current) {
                            PaddingValues(
                                bottom = AppTheme.dimens.bottomNavHeight +
                                        LocalWindowInsets.current.navigationBars.bottom.toDp()
                            )
                        }
                    )
                }
            }
        }
        HomeViewModel.State.ERROR -> Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.general_error),
                style = AppTheme.typography.h6
            )
            Button(onClick = { viewModel.fetch() }) {
                Text(text = stringResource(id = R.string.retry))
            }
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
        ApplicationsViewModel.State.EMPTY -> Box(
            Modifier
                .fillMaxSize()
                .padding(16.dp)) {
            Text(
                text = stringResource(id = R.string.applications_empty_message),
                modifier = Modifier.align(Alignment.Center),
                style = AppTheme.typography.body1,
                textAlign = TextAlign.Center
            )
        }
        ApplicationsViewModel.State.ERROR -> Box(
            Modifier
                .fillMaxSize()
                .padding(16.dp)) {
            Text(
                text = stringResource(id = R.string.general_error),
                modifier = Modifier.align(Alignment.Center),
                style = AppTheme.typography.body1,
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
                modifier = Modifier
                    .statusBarsPadding()
                    .wrapContentWidth(),
                backgroundColor = AppTheme.colors.surface,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        color = AppTheme.colors.primary,
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
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
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.CenterVertically),
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = categoryTab.name, maxLines = 1)
        }
    }
}
