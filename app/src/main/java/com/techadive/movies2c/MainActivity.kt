package com.techadive.movies2c

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.techadive.common.AppRoutes
import com.techadive.designsystem.theme.Movies2cTheme
import com.techadive.movie.ui.search.SearchListView
import com.techadive.movie.viewmodels.HomeViewModel
import com.techadive.movie.viewmodels.SearchViewModel
import com.techadive.movies2c.ui.dashboard.DashboardView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Movies2cTheme {
                MainNavHost(
                    homeViewModel,
                    searchViewModel
                )
            }
        }
    }
}

@Composable
fun MainNavHost(
    homeViewModel: HomeViewModel,
    searchViewModel: SearchViewModel
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
            SearchListView(
                searchViewModel
            )
        }
    }
}