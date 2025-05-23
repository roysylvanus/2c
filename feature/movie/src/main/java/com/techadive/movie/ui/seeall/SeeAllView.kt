package com.techadive.movie.ui.seeall

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techadive.common.R
import com.techadive.common.models.convertToMovieCardData
import com.techadive.designsystem.components.InternetErrorView
import com.techadive.designsystem.components.LoadingView
import com.techadive.designsystem.components.ToolbarView
import com.techadive.designsystem.theme.Movies2cTheme
import com.techadive.movie.ui.components.MovieCardList
import com.techadive.movie.utils.MovieListCategory
import com.techadive.movie.viewmodels.seeall.SeeAllViewModel

const val MOVIE_LIST_CATEGORY = "movie_list_category"

@Composable
fun SeeAllView(
    movieListCategory: MovieListCategory,
    extra: Int?,
    back: () -> Unit,
    showDetails: (Int) -> Unit
) {
    val seeAllViewModel: SeeAllViewModel = hiltViewModel()

    val seeAllUIState = seeAllViewModel.seeAllUIState.collectAsState().value
    val movieList = seeAllUIState.movieList

    LaunchedEffect(Unit) {
        seeAllViewModel.apply {
            clearList()
            fetchListByCategory(movieListCategory, 1, extra)
        }
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
            firstVisible == 0
        }
    }

    LaunchedEffect(shouldLoadNext.value) {
        if (shouldLoadNext.value) {
            movieList?.let {
                seeAllViewModel.fetchListByCategory(movieListCategory, it.page + 1)
            }
        }
    }

    LaunchedEffect(shouldLoadPrevious.value) {
        if (shouldLoadPrevious.value) {
            movieList?.let {
                if (it.page > 1) {
                    seeAllViewModel.fetchListByCategory(
                        movieListCategory = movieListCategory,
                        page = it.page
                    )
                }
            }
        }
    }
    Scaffold(
        topBar = {
            Surface(
                tonalElevation = 4.dp,
                shadowElevation = 4.dp,
                color = Color.Transparent
            ) {
                ToolbarView(
                    title = stringResource(movieListCategory.titleResource),
                    startIconDescription = stringResource(R.string.back),
                    startIconAction = {
                        back()
                    },
                )
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding(),
        containerColor = Movies2cTheme.colors.background
    ) { innerPaddingValues ->

        if (seeAllUIState.isLoading && movieList?.results.isNullOrEmpty()) {
            LoadingView(modifier = Modifier.fillMaxSize(), paddingValues = innerPaddingValues)
        } else if (seeAllUIState.isError && movieList?.results.isNullOrEmpty()) {
            InternetErrorView(
                paddingValues = innerPaddingValues,
                message = stringResource(R.string.something_went_wrong)
            ) {
                seeAllViewModel.apply {
                    clearList()
                    fetchListByCategory(movieListCategory, 1, extra)
                }
            }
        } else if (movieList != null && movieList.results.isNotEmpty()) {

            Box(modifier = Modifier.fillMaxSize()) {
                MovieCardList(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isLoading = seeAllUIState.isError,
                    isError = seeAllUIState.isError,
                    innerPadding = innerPaddingValues,
                    listState = listState,
                    movieCards = movieList.results.map {
                        it.convertToMovieCardData()
                    },
                    showDetails = showDetails
                )
            }

        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.no_results),
                    style = Movies2cTheme.typography.h3,
                    color = Movies2cTheme.colors.onBackground
                )
            }
        }
    }
}