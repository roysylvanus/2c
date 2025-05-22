package com.techadive.movies2c.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.techadive.common.utils.AppRoutes
import com.techadive.movie.ui.favorites.FavoritesView
import com.techadive.movie.ui.home.HomeView
import com.techadive.movie.utils.MovieListCategory
import com.techadive.movie.viewmodels.favorites.FavoritesViewModel
import com.techadive.movie.viewmodels.home.HomeViewModel
import com.techadive.settings.ui.SettingsView
import com.techadive.settings.viewmodels.SettingsViewModel

@Composable
fun DashboardNavHost(
    mainNavController: NavController,
    innerPadding: PaddingValues,
    dashboardNavController: NavHostController,
    homeViewModel: HomeViewModel,
    favoriteViewModel: FavoritesViewModel,
    settingsViewModel: SettingsViewModel,
    seeAll: (MovieListCategory) -> Unit
) {
    NavHost(
        navController = dashboardNavController,
        startDestination = AppRoutes.HOME.route
    ) {
        composable(AppRoutes.HOME.route) {
            HomeView(
                innerPaddingValues = innerPadding,
                homeViewModel = homeViewModel,
                seeAll = seeAll,
            ) { movieId ->
                mainNavController.navigate("${AppRoutes.MOVIE_DETAILS.route}/$movieId")
            }
        }

        composable(AppRoutes.FAVORITES.route) {
            FavoritesView(
                innerPaddingValues = innerPadding,
                favoriteViewModel = favoriteViewModel,
            ) { movieId ->
                mainNavController.navigate("${AppRoutes.MOVIE_DETAILS.route}/$movieId")
            }
        }

        composable(AppRoutes.SETTINGS.route) {
            SettingsView(
                padding = innerPadding,
                appThemeState = settingsViewModel.appTheme.collectAsState(),
                updateTheme = { appTheme ->
                    settingsViewModel.updateAppTheme(appTheme)
                }
            )
        }
    }

}