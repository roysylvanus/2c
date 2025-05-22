package com.techadive.movie.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.techadive.common.models.Movie
import com.techadive.designsystem.theme.Movies2cTheme
import com.techadive.movie.ui.components.HorizontalMovieListSection
import com.techadive.movie.ui.components.MoviesSectionHeader
import com.techadive.movie.viewmodels.home.HomeViewModel
import com.techadive.network.utils.ApiUtils
import kotlin.math.abs

@Composable
fun HomeView(
    innerPaddingValues: PaddingValues,
    homeViewModel: HomeViewModel,
    showDetails: (Int) -> Unit
) {
    LaunchedEffect(Unit) {
        homeViewModel.fetchHomeViewData()
    }

    val homeUIStateValues = homeViewModel.homeUIState.collectAsState().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = innerPaddingValues.calculateTopPadding(),
                bottom = innerPaddingValues.calculateBottomPadding() + 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (homeUIStateValues.isError) {

        } else if (homeUIStateValues.isLoading) {

        } else {

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(com.techadive.common.R.string.now_showing),
                        style = Movies2cTheme.typography.h3,
                        color = Movies2cTheme.colors.onBackground
                    )
                }
            }

            item {
                homeUIStateValues.nowPlayingMovieList?.let { upcomingMovieList ->
                    NowShowingMovieCarousel(
                        movies = upcomingMovieList.results,
                        modifier = Modifier.height(350.dp),
                        showDetails = showDetails
                    )
                }
            }

            item{
                Spacer(modifier = Modifier)
            }

            item {
                MoviesSectionHeader(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    titleResource = com.techadive.common.R.string.upcoming,
                ) {

                }
            }

            homeUIStateValues.upcomingMovieList?.let { moviesList ->
                item {
                    HorizontalMovieListSection(
                        Modifier.padding(start = 16.dp, end = 16.dp),
                        movies = moviesList.results,
                        showDetails = showDetails)
                }
            }

            item {
                MoviesSectionHeader(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    titleResource = com.techadive.common.R.string.popular,
                ) {

                }
            }

            homeUIStateValues.popularMovieList?.let { moviesList ->
                item {
                    HorizontalMovieListSection(
                        Modifier.padding(start = 16.dp, end = 16.dp),
                        movies = moviesList.results,
                        showDetails = showDetails)
                }
            }

            item {
                MoviesSectionHeader(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    titleResource = com.techadive.common.R.string.top_rated,
                ) {

                }
            }

            homeUIStateValues.topRatedMovieList?.let { moviesList ->
                item {
                    HorizontalMovieListSection(
                        Modifier.padding(start = 16.dp, end = 16.dp),
                        movies = moviesList.results,
                        showDetails = showDetails)
                }
            }

        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NowShowingMovieCarousel(
    movies: List<Movie>,
    modifier: Modifier = Modifier,
    showDetails: (Int) -> Unit
) {
    val listState = rememberLazyListState()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = screenWidth * 0.65f

    LazyRow(
        state = listState,
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = (screenWidth - itemWidth) / 2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    ) {
        itemsIndexed(movies) { index, movie ->
            val layoutInfo = listState.layoutInfo
            val itemInfo = layoutInfo.visibleItemsInfo.find { it.index == index }
            val viewportCenter = layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset / 2
            val itemCenter = itemInfo?.offset?.plus(itemInfo.size / 2) ?: 0
            val distanceFromCenter = abs(viewportCenter - itemCenter)

            val scale = 1f - (distanceFromCenter / 1000f).coerceIn(0f, 0.15f)
            val alpha = 1f - (distanceFromCenter / 1000f).coerceIn(0f, 0.5f)

            Box(
                modifier = Modifier
                    .width(itemWidth)
                    .aspectRatio(0.7f)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha
                    }
                    .clip(Movies2cTheme.shapes.medium)
                    .background(Color.LightGray)
                    .clickable { showDetails(movie.id) }
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(ApiUtils.IMAGE_URL + movie.posterPath)
                        .crossfade(true)
                        .build(),
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.4f)),
                                startY = 200f
                            )
                        )
                )
            }
        }
    }
}
