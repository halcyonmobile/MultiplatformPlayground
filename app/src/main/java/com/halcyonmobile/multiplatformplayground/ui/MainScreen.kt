package com.halcyonmobile.multiplatformplayground.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.halcyonmobile.multiplatformplayground.NavigationDirection
import com.halcyonmobile.multiplatformplayground.R
import com.halcyonmobile.multiplatformplayground.navigationDirectionRoutes
import com.halcyonmobile.multiplatformplayground.navigationDirections

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
    Scaffold(
        bottomBar = {
            if (currentRoute in navigationDirections.map { it.route }) {
                BottomAppBar(cutoutShape = CircleShape) {
                    navigationDirections.forEach {
                        Icon(
                            imageVector = vectorResource(id = it.iconRes), Modifier.clickable(
                                onClick = {
                                    if (currentRoute != it.route) {
                                        navController.popBackStack(
                                            navController.graph.startDestination,
                                            false
                                        )
                                        navController.navigate(it.route)
                                    }
                                }).padding(16.dp)
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            if (currentRoute in navigationDirectionRoutes) {
                FloatingActionButton(onClick = { navController.navigate("uploadApplication") }) {
                    Icon(imageVector = vectorResource(id = R.drawable.ic_add))
                }
            }
        },
        isFloatingActionButtonDocked = true
    ) {
        NavHost(
            navController = navController,
            startDestination = NavigationDirection.Home.route,
            builder = {
                composable(NavigationDirection.Home.route) {
                    HomeScreen(onApplicationClicked = { navController.navigate("applicationDetail/${it.id}") })
                }
                composable(NavigationDirection.Favourites.route) {
                    FavouriteScreen(onApplicationClicked = { navController.navigate("applicationDetail/${it.id}") })
                }
                composable(
                    "applicationDetail/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.LongType })
                ) {
                    ApplicationDetail(
                        applicationId = it.arguments!!.getLong("id"),
                        upPress = { navController.popBackStack() })
                }
                composable("uploadApplication") {
                    UploadApplication(upPress = { navController.popBackStack() })
                }
            })
    }
}