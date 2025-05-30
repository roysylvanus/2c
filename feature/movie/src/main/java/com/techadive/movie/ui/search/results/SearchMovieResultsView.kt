package com.techadive.movie.ui.search.results

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techadive.common.R
import com.techadive.common.models.convertToMovieCardData
import com.techadive.designsystem.components.InternetErrorView
import com.techadive.designsystem.components.LoadingView
import com.techadive.designsystem.components.ReadonlyTextField
import com.techadive.designsystem.theme.Movies2cTheme
import com.techadive.movie.ui.components.MovieCardList
import com.techadive.movie.viewmodels.search.SearchMovieResultsViewModel

const val SEARCH_QUERY = "search_query"

@Composable
fun SearchMovieResultsView(
    searchQuery: String?,
    back: () -> Unit,
    openSearch: () -> Unit,
    showDetails: (Int) -> Unit,
) {
    val searchMovieResultsViewModel: SearchMovieResultsViewModel = hiltViewModel()

    val searchResultsUIStateValues =
        searchMovieResultsViewModel.searchMovieResultsUIState.collectAsState().value

    LaunchedEffect(Unit) {
        searchMovieResultsViewModel.searchMovies(query = searchQuery)
    }

    val listState = rememberLazyGridState()
    val shouldLoadNext = remember {
        derivedStateOf {
            val totalItems = listState.layoutInfo.totalItemsCount
            val lastVisibleIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleIndex >= totalItems - 5 // adjust prefetch threshold if needed
        }
    }

    val shouldLoadPrevious = remember {
        derivedStateOf {
            val firstVisible = listState.layoutInfo.visibleItemsInfo.firstOrNull()?.index ?: 0
            firstVisible == 0 // Load previous when scrolling up to top
        }
    }

    LaunchedEffect(shouldLoadNext.value) {
        if (shouldLoadNext.value == true) {
            searchResultsUIStateValues.movieList?.let {
                searchMovieResultsViewModel.searchMovies(
                    query = searchQuery,
                    page = it.page + 1
                )
            }
        }
    }

    LaunchedEffect(shouldLoadPrevious.value) {
        if (shouldLoadPrevious.value) {
            searchResultsUIStateValues.movieList?.page?.let {
                if (it > 1) {
                    searchMovieResultsViewModel.searchMovies(query = searchQuery, page = it)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            SearchViewToolBar(
                searchQuery,
                back,
                openSearch
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding(),
        containerColor = Movies2cTheme.colors.background,
    ) { innerPadding ->

        if (searchResultsUIStateValues.isLoading && searchResultsUIStateValues.movieList?.results.isNullOrEmpty()) {
            LoadingView(modifier = Modifier.fillMaxSize(), paddingValues = innerPadding)
        } else if (searchResultsUIStateValues.isError && searchResultsUIStateValues.movieList?.results.isNullOrEmpty()) {
            InternetErrorView(
                paddingValues = innerPadding,
                message = stringResource(R.string.something_went_wrong)
            ) {
                searchMovieResultsViewModel.searchMovies(query = searchQuery)
            }
        } else if (searchResultsUIStateValues.movieList != null && searchResultsUIStateValues.movieList.results.isNotEmpty()) {
            val moviesWithPosters =
                searchResultsUIStateValues.movieList.results.filter { it.posterPath != null && it.backdropPath != null }

            MovieCardList(
                innerPadding = innerPadding,
                isLoading = searchResultsUIStateValues.isLoading,
                isError = searchResultsUIStateValues.isError,
                movieCards = moviesWithPosters.map { it.convertToMovieCardData() },
                listState = listState,
                showDetails = showDetails
            )
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringResource(
                        R.string.no_results_for_query,
                        searchQuery.orEmpty()
                    ),
                    style = Movies2cTheme.typography.h3,
                    color = Movies2cTheme.colors.onBackground
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchViewToolBar(
    searchQuery: String?,
    back: () -> Unit,
    openSearch: () -> Unit,
) {
    Surface(
        tonalElevation = 4.dp,
        shadowElevation = 4.dp,
        color = Color.Transparent
    ) {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Weight makes sure it doesn't take space meant for nav icon
                    ReadonlyTextField(
                        modifier = Modifier.weight(1f),
                        value = searchQuery,
                        label = R.string.search,
                        icon = R.drawable.ic_search,
                        onValueChange = {},
                        onClick = openSearch
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = back) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        tint = Movies2cTheme.colors.onBackground
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Movies2cTheme.colors.background
            )
        )
    }
}