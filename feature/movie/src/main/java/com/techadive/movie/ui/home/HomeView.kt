package com.techadive.movie.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.techadive.common.AppRoutes
import com.techadive.common.models.Movie
import com.techadive.designsystem.components.ReadonlyTextField
import com.techadive.designsystem.theme.Movies2cTheme
import com.techadive.designsystem.theme.Palette
import com.techadive.movie.ui.MovieCard
import com.techadive.movie.viewmodels.HomeViewModel
import com.techadive.network.utils.ApiUtils
import kotlin.math.abs

@Composable
fun HomeView(
    mainNavController: NavController,
    innerPaddingValues: PaddingValues,
    homeViewModel: HomeViewModel
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
                ReadonlyTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    label = com.techadive.common.R.string.search,
                    icon = com.techadive.common.R.drawable.ic_search,
                    onValueChange = {},
                ) {
                    mainNavController.navigate(AppRoutes.SEARCH.name)
                }
            }

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
                        modifier = Modifier.height(350.dp)
                    )
                }
            }

            item {
                HomeViewSectionHeader(
                    titleResource = com.techadive.common.R.string.upcoming,
                ) {

                }
            }

            homeUIStateValues.upcomingMovieList?.let { moviesList ->
                item {
                    HorizontalMovieListSection(movies = moviesList.results)
                }
            }

            item {
                HomeViewSectionHeader(
                    titleResource = com.techadive.common.R.string.popular,
                ) {

                }
            }

            homeUIStateValues.popularMovieList?.let { moviesList ->
                item {
                    HorizontalMovieListSection(movies = moviesList.results)
                }
            }

            item {
                HomeViewSectionHeader(
                    titleResource = com.techadive.common.R.string.top_rated,
                ) {

                }
            }

            homeUIStateValues.topRatedMovieList?.let { moviesList ->
                item {
                    HorizontalMovieListSection(movies = moviesList.results)
                }
            }

        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NowShowingMovieCarousel(
    movies: List<Movie>,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    val itemSpacing = 16.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = screenWidth * 0.65f

    Box(modifier = modifier) {
        LazyRow(
            state = listState,
            modifier = Modifier.fillMaxWidth()
                .height(350.dp),
            contentPadding = PaddingValues(horizontal = (screenWidth - itemWidth) / 2),
            horizontalArrangement = Arrangement.spacedBy(itemSpacing),
            flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
        ) {
            itemsIndexed(movies) { index, movie ->
                val scale by remember {
                    derivedStateOf {
                        val layoutInfo = listState.layoutInfo
                        val itemInfo = layoutInfo.visibleItemsInfo.find { it.index == index }
                        val viewportCenter =
                            layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset / 2

                        itemInfo?.let {
                            val itemCenter = it.offset + it.size / 2
                            val distanceFromCenter = abs(viewportCenter - itemCenter)

                            1f - (distanceFromCenter / 1000f).coerceIn(0f, 0.15f)
                        } ?: 0.85f
                    }
                }

                val alpha by remember {
                    derivedStateOf {
                        val layoutInfo = listState.layoutInfo
                        val itemInfo = layoutInfo.visibleItemsInfo.find { it.index == index }
                        val viewportCenter =
                            layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset / 2

                        itemInfo?.let {
                            val itemCenter = it.offset + it.size / 2
                            val distanceFromCenter = abs(viewportCenter - itemCenter)

                            1f - (distanceFromCenter / 1000f).coerceIn(0f, 0.5f)
                        } ?: 0.5f
                    }
                }

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
                }
            }
        }
    }
}

@Composable
private fun HomeViewSectionHeader(titleResource: Int, seeAllClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            modifier = Modifier.alignByBaseline(),
            text = stringResource(titleResource),
            style = Movies2cTheme.typography.h3,
            color = Movies2cTheme.colors.onBackground
        )

        TextButton(modifier = Modifier.alignByBaseline(), onClick = { seeAllClick() }) {
            Row {
                Text(
                    modifier = Modifier.alignByBaseline(),
                    text = stringResource(com.techadive.common.R.string.see),
                    style = Movies2cTheme.typography.label,
                    color = Movies2cTheme.colors.onBackground
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = stringResource(com.techadive.common.R.string.all),
                    modifier = Modifier
                        .alignByBaseline()
                        .drawBehind {
                            val underlineHeight = 2.dp.toPx()
                            val y = size.height // Bottom of the text
                            drawLine(
                                color = Palette.orange, // Underline color only
                                start = Offset(0f, y),
                                end = Offset(size.width, y),
                                strokeWidth = underlineHeight
                            )
                        },
                    style = Movies2cTheme.typography.label,
                    color = Movies2cTheme.colors.onBackground
                )
            }
        }
    }

}

@Composable
private fun HorizontalMovieListSection(
    movies: List<Movie>
) {
    val moviesWithPosters = movies.filter { it.posterPath != null }

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(moviesWithPosters.take(10)) { movie ->
            movie.posterPath?.let {
                MovieCard(movie) // custom composable
            }
        }
    }
}