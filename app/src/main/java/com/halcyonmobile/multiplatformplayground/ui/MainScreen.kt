package com.halcyonmobile.multiplatformplayground.ui

import androidx.compose.foundation.Icon
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import com.halcyonmobile.multiplatformplayground.MainViewModel
import com.halcyonmobile.multiplatformplayground.model.ui.ApplicationUiModel
import org.koin.androidx.compose.getViewModel

@Composable
fun MainScreen(
    openApplicationDetail: (Long) -> Unit,
    onUploadApplication: (categoryId: Long) -> Unit
) {
    val viewModel = getViewModel<MainViewModel>()
    val navigationItems by viewModel.navigationItems.collectAsState(emptyList())
    val selectedItem by viewModel.selectedNavigationItem.collectAsState(
        MainViewModel.NavigationItem.Home(isSelected = true)
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(1f)) {
            val onApplicationClicked: (ApplicationUiModel.App) -> Unit = {
                openApplicationDetail(it.id)
            }

            when (selectedItem) {
                is MainViewModel.NavigationItem.Home -> HomeScreen(
                    onApplicationClicked = onApplicationClicked,
                    onUploadApplication = onUploadApplication
                )
                is MainViewModel.NavigationItem.Favourites -> FavouriteScreen(onApplicationClicked = onApplicationClicked)
                is MainViewModel.NavigationItem.Settings -> {
                }
            }
        }
        BottomNavigation {
            navigationItems.forEach {
                BottomNavigationItem(
                    icon = { Icon(asset = vectorResource(id = it.iconRes)) },
                    selected = it.isSelected,
                    onClick = { viewModel.onNavigationItemSelected(it) }
                )
            }
        }
    }
}