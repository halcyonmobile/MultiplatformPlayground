package com.halcyonmobile.multiplatformplayground

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import com.halcyonmobile.multiplatformplayground.model.ui.ApplicationUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel : ViewModel() {

    private val _navigationItems = MutableStateFlow(
        listOf(
            NavigationItem.Home(isSelected = true),
            NavigationItem.Favourites(isSelected = false),
            NavigationItem.Settings(isSelected = false)
        )
    )
    val navigationItems: StateFlow<List<NavigationItem>> = _navigationItems
    val selectedNavigationItem =
        navigationItems.map { it.first { navigationItem -> navigationItem.isSelected } }

    fun onNavigationItemSelected(navigationItem: NavigationItem) {
        _navigationItems.value = _navigationItems.value.map {
            when (it) {
                is NavigationItem.Home -> it.copy(isSelected = it.javaClass == navigationItem.javaClass)
                is NavigationItem.Favourites -> it.copy(isSelected = it.javaClass == navigationItem.javaClass)
                is NavigationItem.Settings -> it.copy(isSelected = it.javaClass == navigationItem.javaClass)
            }
        }
    }

    sealed class NavigationItem(@DrawableRes open val iconRes: Int) {
        abstract val isSelected: Boolean

        data class Home(override val isSelected: Boolean) : NavigationItem(R.drawable.ic_home)

        data class Favourites(override val isSelected: Boolean) :
            NavigationItem(R.drawable.ic_favourites)

        data class Settings(override val isSelected: Boolean) :
            NavigationItem(R.drawable.ic_settings)
    }
}