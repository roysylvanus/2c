package com.techadive.movies2c.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.techadive.common.AppRoutes
import com.techadive.designsystem.theme.Movies2cTheme

@Composable
fun BottomNavView(
    navController: NavController,
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    BottomNavigation(
        backgroundColor = Movies2cTheme.colors.surface,
        elevation = 20.dp
    ) {
        appBottomNavItems.forEach { navItem ->
            val isSelected = currentRoute == navItem.route
            val titleResource = stringResource(id = navItem.title)

            BottomNavigationItem(
                selected = isSelected,
                onClick = {
                    if (currentRoute != navItem.route) {
                        navController.navigate(navItem.route)
                    }
                },
                icon = {
                    Icon(
                        imageVector = navItem.imageVector,
                        contentDescription = titleResource,
                        tint = if (isSelected) Movies2cTheme.colors.primary else Movies2cTheme.colors.onSurface,
                        modifier = Modifier.size(15.dp),
                    )
                },
                label = {
                    Text(
                        text = titleResource,
                        textAlign = TextAlign.Center,
                        style = Movies2cTheme.typography.body5,
                        color = if (isSelected) Movies2cTheme.colors.primary else Movies2cTheme.colors.onSurface
                    )
                },
                selectedContentColor = Movies2cTheme.colors.primary,
                unselectedContentColor = Movies2cTheme.colors.onSurface,
            )
        }
    }
}

val appBottomNavItems = listOf(
    AppBottomNavItem(com.techadive.common.R.string.home, AppRoutes.HOME.route, Icons.Filled.Home),
    AppBottomNavItem(com.techadive.common.R.string.favorites, AppRoutes.FAVORITES.route, Icons.Filled.Favorite),
    AppBottomNavItem(com.techadive.common.R.string.settings, AppRoutes.SETTINGS.route, Icons.Filled.Settings)
)

data class AppBottomNavItem(
    val title: Int,
    val route: String,
    val imageVector: ImageVector
)