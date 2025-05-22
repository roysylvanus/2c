package com.techadive.movie.ui.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.techadive.designsystem.components.InternetErrorView
import com.techadive.designsystem.components.LoadingView
import com.techadive.designsystem.theme.Movies2cTheme
import com.techadive.designsystem.theme.Palette
import com.techadive.movie.ui.components.HorizontalMovieListSection
import com.techadive.movie.ui.components.MoviesSectionHeader
import com.techadive.movie.ui.details.components.BackdropHeaderImage
import com.techadive.movie.ui.details.components.GenreChips
import com.techadive.movie.ui.details.components.MovieMetadataSection
import com.techadive.movie.ui.details.components.ProductionCompaniesView
import com.techadive.movie.ui.details.components.TopBarWithFade
import com.techadive.movie.utils.MovieListCategory
import com.techadive.movie.viewmodels.details.MovieDetailsViewModel

const val MOVIE_ID = "movie_id"

@Composable
fun MovieDetailsView(
    movieId: Int?,
    movieDetailsViewModel: MovieDetailsViewModel,
    seeAll: (MovieListCategory, Int?) -> Unit,
    showDetails: (Int) -> Unit,
    shareUrl: (String, String?) -> Unit,
    back: () -> Unit,
) {
    val state = movieDetailsViewModel.movieDetailsUIState.collectAsState().value
    val movieDetails = state.movieDetails
    val scrollState = rememberScrollState()

    LaunchedEffect(movieId) {
        movieId?.let {
            movieDetailsViewModel.onEvent(
                MovieDetailsViewModel.MovieDetailsEvent.FetchMovieDetail(it)
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .background(
                Movies2cTheme.colors.background
            )
    ) {
        if (state.isLoading) {
            LoadingView(PaddingValues())
        } else if (state.isError) {
            InternetErrorView(
                paddingValues = PaddingValues(),
                message = stringResource(com.techadive.common.R.string.something_went_wrong)
            ) {
                movieId?.let {
                    movieDetailsViewModel.onEvent(
                        MovieDetailsViewModel.MovieDetailsEvent.FetchMovieDetail(it)
                    )
                }
            }
        } else {
            movieDetails?.let { movie ->
                // Backdrop image with gradient overlay
                BackdropHeaderImage(movie.backdropPath)

                // Details overlaid on image
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .fillMaxSize()
                        .padding(top = 240.dp) // Adjust based on image height
                        .background(Movies2cTheme.colors.background)
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Absolute.Right
                    ) {

                        androidx.compose.material3.IconButton(onClick = {
                            movieDetailsViewModel.onEvent(
                                MovieDetailsViewModel.MovieDetailsEvent.UpdateFavoriteStatus(
                                    movie.id, !movie.isFavorite
                                )
                            )
                        }) {
                            Icon(
                                imageVector = if (movie.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = stringResource(com.techadive.common.R.string.favorites),
                                tint = if (movie.isFavorite) Palette.red else
                                    Movies2cTheme.colors.onBackground
                            )
                        }
                    }

                    Text(
                        text = movie.title,
                        style = Movies2cTheme.typography.h3,
                        color = Movies2cTheme.colors.onBackground
                    )

                    AnimatedVisibility(visible = movie.tagline.isNotEmpty()) {
                        Text(
                            text = "\"${movie.tagline}\"",
                            style = Movies2cTheme.typography.body3.copy(fontStyle = FontStyle.Italic),
                            color = Movies2cTheme.colors.onSurface,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    MovieMetadataSection(movie)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = movie.overview,
                        style = Movies2cTheme.typography.body3,
                        color = Movies2cTheme.colors.onBackground
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    GenreChips(genres = movie.genres)

                    Spacer(modifier = Modifier.height(16.dp))

                    movie.productionCompanies?.let { ProductionCompaniesView(it) }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (state.movieList != null && state.movieList.results.isNotEmpty()) {
                        MoviesSectionHeader(
                            titleResource = com.techadive.common.R.string.recommended,
                        ) {
                            seeAll(MovieListCategory.RECOMMENDED, movieId)
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        HorizontalMovieListSection(
                            movies = state.movieList.results,
                            showDetails = showDetails
                        )
                    }
                }
            }
        }
        val endIcon = if (movieDetails?.posterPath != null) Icons.Default.Share else null

        TopBarWithFade(scrollState, endIcon, back) {
            shareUrl(
                movieDetails?.title.orEmpty(),
                movieDetails?.posterPath
            )
        }
    }
}
