package com.halcyonmobile.multiplatformplayground

import androidx.annotation.DrawableRes

sealed class NavigationDirection(val route: String, @DrawableRes open val iconRes: Int) {

    object Home : NavigationDirection("home", R.drawable.ic_home)

    object Favourites : NavigationDirection("favourites", R.drawable.ic_favourites)
}

val navigationDirections = listOf(
    NavigationDirection.Home,
    NavigationDirection.Favourites,
)

val navigationDirectionRoutes = navigationDirections.map { it.route }
