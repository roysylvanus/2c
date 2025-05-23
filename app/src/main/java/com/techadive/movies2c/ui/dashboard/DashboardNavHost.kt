package com.techadive.movies2c.ui.dashboard

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.techadive.common.utils.AppRoutes
import com.techadive.movie.ui.favorites.FavoritesView
import com.techadive.movie.ui.home.HomeView
import com.techadive.movie.utils.MovieListCategory
import com.techadive.movies2c.RoutesHelper
import com.techadive.settings.ui.SettingsView

@Composable
fun DashboardNavHost(
    mainNavController: NavController,
    innerPadding: PaddingValues,
    dashboardNavController: NavHostController,
    seeAll: (MovieListCategory) -> Unit
) {
    NavHost(
        navController = dashboardNavController,
        startDestination = AppRoutes.HOME.route
    ) {
        composable(AppRoutes.HOME.route) {
            HomeView(
                innerPaddingValues = innerPadding,
                seeAll = seeAll,
            ) { movieId ->
                mainNavController.navigate(RoutesHelper.movieDetailsRoute(movieId))
            }
        }

        composable(AppRoutes.FAVORITES.route) {
            FavoritesView(
                innerPaddingValues = innerPadding,
            ) { movieId ->
                mainNavController.navigate(RoutesHelper.movieDetailsRoute(movieId))
            }
        }

        composable(AppRoutes.SETTINGS.route) {
            SettingsView(
                padding = innerPadding,
            )
        }
    }

}