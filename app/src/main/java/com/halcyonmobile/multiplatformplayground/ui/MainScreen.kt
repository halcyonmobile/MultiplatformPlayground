package com.halcyonmobile.multiplatformplayground.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.halcyonmobile.multiplatformplayground.BottomNavigationScreen
import com.halcyonmobile.multiplatformplayground.bottomNavigationScreens

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
            if (currentRoute in bottomNavigationScreens.map { it.route }) {
                BottomNavigation {
                    bottomNavigationScreens.forEach {
                        BottomNavigationItem(
                            icon = { Icon(imageVector = vectorResource(id = it.iconRes)) },
                            selected = currentRoute == it.route,
                            onClick = {
                                if (currentRoute != it.route) {
                                    navController.popBackStack(
                                        navController.graph.startDestination,
                                        false
                                    )
                                    navController.navigate(it.route)
                                }
                            }
                        )
                    }
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = BottomNavigationScreen.Home.route,
            builder = {
                composable(BottomNavigationScreen.Home.route) {
                    HomeScreen(
                        onApplicationClicked = { navController.navigate("applicationDetail/${it.id}") },
                        onUploadApplication = { navController.navigate("uploadApplication/${it}") }
                    )
                }
                composable(BottomNavigationScreen.Favourites.route) {
                    FavouriteScreen(onApplicationClicked = { navController.navigate("applicationDetail/${it.id}") })
                }
                composable(BottomNavigationScreen.Settings.route) {
                    // TODO
                }
                composable(
                    "applicationDetail/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.LongType })
                ) {
                    ApplicationDetail(
                        applicationId = it.arguments!!.getLong("id"),
                        upPress = { navController.popBackStack() })
                }
                composable(
                    "uploadApplication/{categoryId}",
                    arguments = listOf(navArgument("categoryId") { type = NavType.LongType })
                ) {
                    UploadApplication(
                        initialCategoryId = it.arguments!!.getLong("categoryId"),
                        upPress = { navController.popBackStack() }
                    )
                }
            })
    }
}