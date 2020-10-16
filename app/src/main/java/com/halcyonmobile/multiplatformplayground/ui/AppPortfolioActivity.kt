package com.halcyonmobile.multiplatformplayground.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Icon
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.vectorResource
import com.halcyonmobile.multiplatformplayground.MainViewModel
import com.halcyonmobile.multiplatformplayground.R
import com.halcyonmobile.multiplatformplayground.ui.theme.MultiplatformPlaygroundTheme
import org.koin.androidx.compose.getViewModel

class AppPortfolioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.BaseTheme_App)
        super.onCreate(savedInstanceState)
        setContent {
            MultiplatformPlaygroundTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Navigation()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
//        applyEdgeToEdgeFlags()
    }

    @Composable
    private fun Navigation() {
        val viewModel = getViewModel<MainViewModel>()
        val navigationItems by viewModel.navigationItems.collectAsState(emptyList())
        val selectedItem by viewModel.selectedNavigationItem.collectAsState(
            MainViewModel.NavigationItem.Home(isSelected = true)
        )
        val showDetail by viewModel.showDetail.collectAsState(null)

        if (showDetail != null) {
            ApplicationDetail(applicationId = showDetail!!)
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.weight(1f)) {
                    when (selectedItem) {
                        is MainViewModel.NavigationItem.Home -> HomeScreen(viewModel::onApplicationClicked)
                        is MainViewModel.NavigationItem.Favourites -> {
                        }
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
    }
}
