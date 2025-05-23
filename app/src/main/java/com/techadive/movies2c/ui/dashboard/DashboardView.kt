package com.techadive.movies2c.ui.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.techadive.common.utils.AppRoutes
import com.techadive.designsystem.components.ToolbarView
import com.techadive.designsystem.theme.Movies2cTheme
import com.techadive.movie.utils.MovieListCategory
import com.techadive.movies2c.ui.components.BottomNavView

@Composable
fun DashboardView(
    mainNavController: NavController,
    seeAll: (MovieListCategory) -> Unit
) {
    val dashboardNavController = rememberNavController()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding(),
        topBar = {
            DashboardToolbar(mainNavController)
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
            seeAll
        )
    }
}

@Composable
private fun DashboardToolbar(
    mainNavController: NavController
) {
    Surface(
        tonalElevation = 4.dp,
        shadowElevation = 4.dp,
        color = Color.Transparent
    ) {
        ToolbarView(
            startIcon = com.techadive.movies2c.R.drawable.c_icon,
            endContent = {
                IconButton(onClick = {
                    mainNavController.navigate(AppRoutes.SEARCH.name)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(com.techadive.common.R.string.search),
                        tint = Movies2cTheme.colors.onBackground
                    )
                }
            },
        )
    }
}