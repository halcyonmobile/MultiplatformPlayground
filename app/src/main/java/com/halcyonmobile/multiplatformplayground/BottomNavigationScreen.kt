package com.halcyonmobile.multiplatformplayground

import androidx.annotation.DrawableRes

sealed class BottomNavigationScreen(val route: String, @DrawableRes open val iconRes: Int) {

    object Home : BottomNavigationScreen("home", R.drawable.ic_home)

    object Favourites : BottomNavigationScreen("favourites", R.drawable.ic_favourites)

    object Settings : BottomNavigationScreen("settings", R.drawable.ic_settings)
}

val bottomNavigationScreens = listOf(
    BottomNavigationScreen.Home,
    BottomNavigationScreen.Favourites,
    BottomNavigationScreen.Settings
)