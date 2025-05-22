package com.techadive.movies2c

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.techadive.common.AppRoutes
import com.techadive.designsystem.theme.Movies2cTheme
import com.techadive.movie.ui.details.MOVIE_ID
import com.techadive.movie.ui.details.MovieDetailsView
import com.techadive.movie.ui.search.SearchMovieResultsView
import com.techadive.movie.ui.search.RecentSearchView
import com.techadive.movie.ui.search.SEARCH_QUERY
import com.techadive.movie.viewmodels.details.MovieDetailsViewModel
import com.techadive.movie.viewmodels.favorites.FavoritesViewModel
import com.techadive.movie.viewmodels.home.HomeViewModel
import com.techadive.movie.viewmodels.search.RecentSearchViewModel
import com.techadive.movie.viewmodels.search.SearchMovieResultsViewModel
import com.techadive.movies2c.ui.dashboard.DashboardView
import com.techadive.network.utils.ApiUtils
import com.techadive.settings.viewmodels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val searchMovieResultsViewModel: SearchMovieResultsViewModel by viewModels()
    private val recentSearchViewModel: RecentSearchViewModel by viewModels()
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()
    private val favoriteViewModel: FavoritesViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Movies2cTheme(
                appTheme = settingsViewModel.appTheme.collectAsState().value
            ) {
                MainNavHost(
                    homeViewModel,
                    searchMovieResultsViewModel,
                    recentSearchViewModel,
                    movieDetailsViewModel,
                    favoriteViewModel,
                    settingsViewModel,
                    ::shareUrl
                )
            }
        }
    }

    private fun shareUrl(movieTitle: String, posterPath: String?) {
        if (posterPath != null) {
            val url = ApiUtils.IMAGE_URL + posterPath

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = resources.getString(com.techadive.common.R.string.mime_type_text)
                putExtra(
                    Intent.EXTRA_SUBJECT,
                    resources.getString(com.techadive.common.R.string.check_out_the_movie)
                )
                putExtra(Intent.EXTRA_TEXT, "$movieTitle \n${url}")
            }
            startActivity(
                Intent.createChooser(
                    shareIntent,
                    resources.getString(com.techadive.common.R.string.share_via)
                )
            )
        } else {
            showToast(com.techadive.common.R.string.error_missing_url)
        }
    }

    private fun showToast(messageResource: Int) {
        Toast.makeText(
            this,
            resources.getString(messageResource),
            Toast.LENGTH_SHORT
        ).show()
    }
}

@Composable
fun MainNavHost(
    homeViewModel: HomeViewModel,
    searchMovieResultsViewModel: SearchMovieResultsViewModel,
    recentSearchViewModel: RecentSearchViewModel,
    movieDetailsViewModel: MovieDetailsViewModel,
    favoriteViewModel: FavoritesViewModel,
    settingsViewModel: SettingsViewModel,
    shareUrl: (String, String?) -> Unit,
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppRoutes.DASHBOARD.route) {
        composable(AppRoutes.DASHBOARD.route) {
            DashboardView(
                navController,
                homeViewModel,
                favoriteViewModel,
                settingsViewModel
            )
        }

        composable(AppRoutes.SEARCH.route) {
            RecentSearchView(
                recentSearchViewModel = recentSearchViewModel,
                backClicked = {
                    navController.navigateUp()
                },
                showResults = { query ->
                    Log.d(
                        "NavigationDebug",
                        "Navigating to: ${AppRoutes.SEARCH_MOVIE_RESULTS.route}/$query"
                    )

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
            ) { movieId ->
                navController.navigate("${AppRoutes.MOVIE_DETAILS.route}/$movieId")
            }
        }

        composable(
            route = "${AppRoutes.MOVIE_DETAILS.route}/{$MOVIE_ID}",
            arguments = listOf(
                navArgument(MOVIE_ID) {
                    type = NavType.IntType
                }
            )
        ) { backStack ->
            val movieId = backStack.arguments?.getInt(MOVIE_ID)

            MovieDetailsView(
                movieId = movieId,
                movieDetailsViewModel = movieDetailsViewModel,
                showDetails = { movieId ->
                    navController.navigate("${AppRoutes.MOVIE_DETAILS.route}/$movieId")
                },
                shareUrl = shareUrl
            ) {
                navController.navigateUp()
            }
        }
    }
}