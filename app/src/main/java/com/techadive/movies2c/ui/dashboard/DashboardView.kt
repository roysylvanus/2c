package com.techadive.movies2c.ui.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.techadive.designsystem.components.ToolbarView
import com.techadive.designsystem.theme.Movies2cTheme
import com.techadive.movie.viewmodels.HomeViewModel
import com.techadive.movies2c.ui.DashboardNavHost
import com.techadive.movies2c.ui.components.BottomNavView

@Composable
fun DashboardView(
    mainNavController: NavController,
    homeViewModel: HomeViewModel
) {
    val dashboardNavController = rememberNavController()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding(),
        topBar = {
            ToolbarView()
        },
        bottomBar = {
            BottomNavView(dashboardNavController)
        },
        containerColor = Movies2cTheme.colors.background
    ) { innerPadding ->

        DashboardNavHost(
            mainNavController,
            innerPadding,
            dashboardNavController,
            homeViewModel
        )
    }
}