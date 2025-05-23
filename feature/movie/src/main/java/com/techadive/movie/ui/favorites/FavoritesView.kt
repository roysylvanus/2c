package com.techadive.movie.ui.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.techadive.designsystem.theme.Movies2cTheme
import com.techadive.movie.ui.components.MovieCardList
import com.techadive.movie.viewmodels.favorites.FavoritesViewModel

@Composable
fun FavoritesView(
    innerPaddingValues: PaddingValues,
    showDetails: (Int) -> Unit
) {
    val favoriteViewModel: FavoritesViewModel = hiltViewModel()

    val favoritesUIStateValues = favoriteViewModel.favoritesUIState.collectAsState().value
    val listState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        favoriteViewModel.fetchFavorites()
    }

    if (favoritesUIStateValues.favorites.isEmpty()) {

        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(com.techadive.common.R.string.no_favorites),
                style = Movies2cTheme.typography.h3,
                color = Movies2cTheme.colors.onBackground
            )
        }

    } else {

        MovieCardList(
            innerPadding = innerPaddingValues,
            listState = listState,
            movieCards = favoritesUIStateValues.favorites,
            showDetails = showDetails
        )
    }
}