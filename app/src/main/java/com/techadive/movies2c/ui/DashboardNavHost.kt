package com.techadive.movies2c.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.techadive.common.AppRoutes
import com.techadive.movie.ui.favorites.FavoritesView
import com.techadive.movie.ui.home.HomeView
import com.techadive.movie.viewmodels.HomeViewModel
import com.techadive.settings.SettingsView

@Composable
fun DashboardNavHost(
    mainNavController: NavController,
    innerPadding: PaddingValues,
    dashboardNavController: NavHostController,
    homeViewModel: HomeViewModel
) {
    NavHost(
        navController = dashboardNavController,
        startDestination = AppRoutes.HOME.route
    ) {
        composable(AppRoutes.HOME.route) {
            HomeView(
                mainNavController = mainNavController,
                innerPaddingValues = innerPadding,
                homeViewModel = homeViewModel
            )
        }

        composable(AppRoutes.FAVORITES.route) {
            FavoritesView(navController = dashboardNavController)
        }

        composable(AppRoutes.SETTINGS.route) {
            SettingsView()
        }
    }

}