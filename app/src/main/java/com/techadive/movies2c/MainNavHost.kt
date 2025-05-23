package com.techadive.movies2c

import com.techadive.movie.ui.details.MOVIE_ID
import com.techadive.movie.ui.details.MovieDetailsView
import com.techadive.movie.ui.search.recentsearch.RecentSearchList
import com.techadive.movie.ui.search.results.SearchMovieResultsView
import com.techadive.movie.ui.search.recentsearch.SearchQueryView
import com.techadive.movie.ui.search.results.SEARCH_QUERY
import com.techadive.movie.ui.seeall.MOVIE_LIST_CATEGORY
import com.techadive.movie.ui.seeall.SeeAllView
import com.techadive.movie.utils.getMovieCategory
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.techadive.common.utils.AppRoutes
import com.techadive.movies2c.ui.dashboard.DashboardView
import androidx.compose.runtime.Composable

@Composable
fun MainNavHost(
    shareUrl: (String, String?) -> Unit,
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppRoutes.DASHBOARD.route) {
        composable(AppRoutes.DASHBOARD.route) {
            DashboardView(
                navController,
            ) { movieCategory ->
                navController.navigate(RoutesHelper.seeAllRoute(movieCategory.value, 0))
            }
        }

        composable(AppRoutes.SEARCH.route) {
            SearchQueryView(
                showRecentSearches = {
                    navController.navigate(AppRoutes.RECENT_SEARCH.route)
                },
                backClicked = {
                    navController.navigateUp()
                },
                showResults = { query ->
                    navController.navigate(RoutesHelper.searchMovieResultsRoute(query))
                }
            )
        }

        composable(AppRoutes.RECENT_SEARCH.route) {
            RecentSearchList(
                navController = navController,
            ) { query ->
                navController.navigate(RoutesHelper.searchMovieResultsRoute(query))
            }
        }

        composable(
            route = "${AppRoutes.SEARCH_MOVIE_RESULTS.route}/{$SEARCH_QUERY}",
            arguments = listOf(
                navArgument(SEARCH_QUERY) {
                    // search query to fetch and display movies by query
                    type = NavType.StringType
                }
            )
        ) { backStack ->
            val searchQuery = backStack.arguments?.getString(SEARCH_QUERY)

            SearchMovieResultsView(
                searchQuery = searchQuery,
                back = { navController.navigateUp() },
                openSearch = { navController.navigateUp() }
            ) { movieId ->
                navController.navigate(RoutesHelper.movieDetailsRoute(movieId))
            }
        }

        composable(
            route = "${AppRoutes.MOVIE_DETAILS.route}/{$MOVIE_ID}",
            arguments = listOf(
                navArgument(MOVIE_ID) {
                    // Movie ID to fetch and display details
                    type = NavType.IntType
                }
            )
        ) { backStack ->
            val movieId = backStack.arguments?.getInt(MOVIE_ID)

            MovieDetailsView(
                movieId = movieId,
                seeAll = { movieCategory, movieId ->
                    navController.navigate(RoutesHelper.seeAllRoute(movieCategory.value, movieId))
                },
                showDetails = { movieId ->
                    navController.navigate(RoutesHelper.movieDetailsRoute(movieId))
                },
                shareUrl = shareUrl
            ) {
                navController.navigateUp()
            }
        }

        composable(
            route = "${AppRoutes.SEE_ALL.route}/{$MOVIE_LIST_CATEGORY}/{$MOVIE_ID}",
            arguments = listOf(
                navArgument(MOVIE_LIST_CATEGORY) {
                    // Movie category to fetch and display recommended videos
                    type = NavType.StringType
                },
                navArgument(MOVIE_ID) {
                    // Movie ID to fetch and display recommended videos
                    type = NavType.IntType
                },
            )
        ) { backStack ->
            val movieListCategory = backStack.arguments?.getString(MOVIE_LIST_CATEGORY)
            val extra = backStack.arguments?.getInt(MOVIE_ID)

            SeeAllView(
                movieListCategory = movieListCategory.orEmpty().getMovieCategory(),
                extra = extra,
                back = {
                    navController.navigateUp()
                }
            ) { movieId ->
                navController.navigate(RoutesHelper.movieDetailsRoute(movieId))
            }
        }
    }
}

object RoutesHelper {
    fun seeAllRoute(category: String, extra: Int?) = "${AppRoutes.SEE_ALL.route}/$category/$extra"
    fun movieDetailsRoute(movieId: Int) = "${AppRoutes.MOVIE_DETAILS.route}/$movieId"
    fun searchMovieResultsRoute(query: String?) = "${AppRoutes.SEARCH_MOVIE_RESULTS.route}/$query"
}