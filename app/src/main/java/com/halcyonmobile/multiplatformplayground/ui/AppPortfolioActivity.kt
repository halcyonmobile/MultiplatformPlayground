package com.halcyonmobile.multiplatformplayground.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Icon
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.halcyonmobile.multiplatformplayground.MainViewModel
import com.halcyonmobile.multiplatformplayground.shared.util.viewModel
import com.halcyonmobile.multiplatformplayground.ui.theme.MultiplatformPlaygroundTheme
import com.halcyonmobile.multiplatformplayground.util.applyEdgeToEdgeFlags
import com.halcyonmobile.multiplatformplayground.viewmodel.HomeViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

class AppPortfolioActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    // TODO Come up with a solution properly provide these to composable functions
    private val viewModel: MainViewModel by viewModel()
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultiplatformPlaygroundTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Navigation(viewModel)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
//        applyEdgeToEdgeFlags()
    }

    @Preview(showBackground = true)
    @Composable
    fun MainPreview() {
        Navigation(viewModel)
    }

    @Composable
    private fun Navigation(viewModel: MainViewModel) {
        val navigationItems by viewModel.navigationItems.collectAsState(emptyList())
        val selectedItem by viewModel.selectedNavigationItem.collectAsState(
            MainViewModel.NavigationItem.Home(isSelected = true)
        )

        Column(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.weight(1f)) {
                when (selectedItem) {
                    is MainViewModel.NavigationItem.Home -> HomeScreen(viewModel = homeViewModel)
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
