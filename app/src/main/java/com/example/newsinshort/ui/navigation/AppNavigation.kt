package com.example.newsinshort.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsinshort.ui.screens.HomeScreen


@Composable
fun AppNavigationGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.HOME_ROUTE) {
        composable(Routes.HOME_ROUTE) {
            HomeScreen()
        }
    }
}