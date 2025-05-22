package com.techadive.movie.ui.favorites

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.techadive.movie.ui.components.MovieCardList
import com.techadive.movie.viewmodels.favorites.FavoritesViewModel

@Composable
fun FavoritesView(
    innerPaddingValues: PaddingValues,
    favoriteViewModel: FavoritesViewModel,
    showDetails: (Int) -> Unit
) {

    val favoritesUIStateValues = favoriteViewModel.favoritesUIState.collectAsState().value
    val listState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        favoriteViewModel.fetchFavorites()
    }

    MovieCardList(
        innerPadding = innerPaddingValues,
        listState = listState,
        movieCards = favoritesUIStateValues.favorites,
        showDetails = showDetails
    )
}