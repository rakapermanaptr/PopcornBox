package com.creospace.popcornbox.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.creospace.popcornbox.presentation.screens.details.MovieDetailsScreen
import com.creospace.popcornbox.presentation.screens.main.MainScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = NavigationItem.Home.route,
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(NavigationItem.Home.route) {
            MainScreen(
                toDetails = {
                    navController.navigate(Screen.DETAILS.name)
                }
            )
        }
        composable(NavigationItem.Details.route) {
            MovieDetailsScreen()
        }
    }
}