package com.techadive.movies2c

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.techadive.common.AppRoutes
import com.techadive.designsystem.theme.Movies2cTheme
import com.techadive.movie.ui.search.SearchMovieResultsView
import com.techadive.movie.ui.search.RecentSearchView
import com.techadive.movie.ui.search.SEARCH_QUERY
import com.techadive.movie.viewmodels.HomeViewModel
import com.techadive.movie.viewmodels.search.RecentSearchViewModel
import com.techadive.movie.viewmodels.search.SearchMovieResultsViewModel
import com.techadive.movies2c.ui.dashboard.DashboardView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val searchMovieResultsViewModel: SearchMovieResultsViewModel by viewModels()
    private val recentSearchViewModel: RecentSearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Movies2cTheme {
                MainNavHost(
                    homeViewModel,
                    searchMovieResultsViewModel,
                    recentSearchViewModel
                )
            }
        }
    }
}

@Composable
fun MainNavHost(
    homeViewModel: HomeViewModel,
    searchMovieResultsViewModel: SearchMovieResultsViewModel,
    recentSearchViewModel: RecentSearchViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppRoutes.DASHBOARD.route) {
        composable(AppRoutes.DASHBOARD.route) {
            DashboardView(
                navController,
                homeViewModel
            )
        }

        composable(AppRoutes.SEARCH.route) {
            RecentSearchView(
                recentSearchViewModel = recentSearchViewModel,
                backClicked = {
                    navController.navigateUp()
                },
                showResults = { query ->
                    Log.d("NavigationDebug", "Navigating to: ${AppRoutes.SEARCH_MOVIE_RESULTS.route}/$query")

                    navController.navigate("${AppRoutes.SEARCH_MOVIE_RESULTS.route}/$query")
                }
            )
        }

        composable(
            route = "${AppRoutes.SEARCH_MOVIE_RESULTS.route}/{$SEARCH_QUERY}",
            arguments = listOf(
                navArgument(SEARCH_QUERY) {
                    type = NavType.StringType
                }
            )
        ) { backStack ->
            val searchQuery = backStack.arguments?.getString(SEARCH_QUERY)

            SearchMovieResultsView(
                searchQuery = searchQuery,
                searchMovieResultsViewModel = searchMovieResultsViewModel,
                back = { navController.navigateUp() },
                openSearch = { navController.navigateUp() }
            )
        }
    }
}