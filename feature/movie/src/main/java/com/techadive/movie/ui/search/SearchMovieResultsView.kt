package com.techadive.movie.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.techadive.designsystem.components.ReadonlyTextField
import com.techadive.designsystem.theme.Movies2cTheme
import com.techadive.movie.ui.MovieCard
import com.techadive.movie.viewmodels.search.SearchMovieResultsViewModel

const val SEARCH_QUERY = "search_query"

@Composable
fun SearchMovieResultsView(
    searchQuery: String?,
    searchMovieResultsViewModel: SearchMovieResultsViewModel,
    back: () -> Unit,
    openSearch: () -> Unit,
) {

    val searchResultsUIStateValues =
        searchMovieResultsViewModel.searchMovieResultsUIState.collectAsState().value

    LaunchedEffect(Unit) {
        searchMovieResultsViewModel.searchMovies(query = searchQuery)
    }

    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 4.dp,
                color = Movies2cTheme.colors.background,
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .wrapContentHeight()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {
                        back()
                    }) {
                        Icon(
                            modifier = Modifier
                                .size(30.dp),
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(com.techadive.common.R.string.back),
                            tint = Movies2cTheme.colors.onBackground,
                        )
                    }

                    ReadonlyTextField(
                        modifier = Modifier
                            .weight(1f),
                        value = searchQuery,
                        label = com.techadive.common.R.string.search,
                        icon = com.techadive.common.R.drawable.ic_search,
                        onValueChange = {},
                    ) {
                        openSearch()
                    }
                }
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding(),
        containerColor = Movies2cTheme.colors.background,
    ) { innerPadding ->

        if (searchResultsUIStateValues.movieList != null) {
            val moviesWithPosters =
                searchResultsUIStateValues.movieList.results.filter { it.posterPath != null }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(moviesWithPosters) { movie ->
                    MovieCard(movie) // custom composable
                }
            }
        }
    }

}