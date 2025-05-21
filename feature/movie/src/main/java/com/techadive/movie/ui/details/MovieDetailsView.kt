package com.techadive.movie.ui.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.techadive.common.getYear
import com.techadive.common.models.Genre
import com.techadive.common.models.MovieDetails
import com.techadive.common.models.ProductionCompany
import com.techadive.common.roundTo2Dec
import com.techadive.designsystem.components.ToolbarView
import com.techadive.designsystem.theme.Movies2cTheme
import com.techadive.movie.ui.components.HorizontalMovieListSection
import com.techadive.movie.ui.components.MoviesSectionHeader
import com.techadive.movie.viewmodels.details.MovieDetailsViewModel
import com.techadive.network.utils.ApiUtils

const val MOVIE_ID = "movie_id"

@Composable
fun MovieDetailsView(
    movieId: Int?,
    movieDetailsViewModel: MovieDetailsViewModel,
    back: () -> Unit,
) {
    val state = movieDetailsViewModel.movieDetailsUIState.collectAsState().value
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
        state.movieDetails?.let { movie ->
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
                       titleResource = com.techadive.common.R.string.more_like_this,
                   ) {

                   }
                   Spacer(modifier = Modifier.height(16.dp))

                   HorizontalMovieListSection(movies = state.movieList.results) {

                   }
               }
            }
        }

        TopBarWithFade(scrollState, back)
    }
}

@Composable
fun BackdropHeaderImage(backdropPath: String?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        AsyncImage(
            model = ApiUtils.IMAGE_URL + backdropPath,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Movies2cTheme.colors.background),
                        startY = 100f
                    )
                )
        )
    }
}

@Composable
fun MovieMetadataSection(movie: MovieDetails) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconText(
            icon = com.techadive.common.R.drawable.ic_star,
            text = "${movie.voteAverage.roundTo2Dec()} / 10"
        )
        IconText(
            icon = com.techadive.common.R.drawable.ic_access_time,
            text = "${movie.runtime} min"
        )
        movie.releaseDate?.getYear()
            ?.let { IconText(icon = com.techadive.common.R.drawable.ic_calendar_today, text = it) }
    }
}

@Composable
fun IconText(
    icon: Int,
    text: String,
    modifier: Modifier = Modifier,
    iconTint: Color = Movies2cTheme.colors.primary,
    textColor: Color = Movies2cTheme.colors.onBackground,
    iconSize: Dp = 20.dp,
    spacing: Dp = 6.dp
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing),
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(iconSize)
        )
        Text(
            text = text,
            color = textColor,
            style = Movies2cTheme.typography.body4
        )
    }
}


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
@Composable
fun GenreChips(genres: List<Genre>) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        genres.forEach {
            Chip(
                onClick = {},
                colors = ChipDefaults.chipColors(
                    backgroundColor = Movies2cTheme.colors.onBackground.copy(
                        alpha = 0.25f
                    )
                )
            ) {
                Text(
                    it.name,
                    style = Movies2cTheme.typography.h7,
                    color = Movies2cTheme.colors.onBackground
                )
            }
        }
    }
}

@Composable
fun ProductionCompaniesView(companies: List<ProductionCompany>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(com.techadive.common.R.string.produced_by),
            style = Movies2cTheme.typography.h5,
            color = Movies2cTheme.colors.onSurface
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            companies.forEach { company ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (company.logoPath?.isNotEmpty() == true) {
                        AsyncImage(
                            model = ApiUtils.IMAGE_URL + company.logoPath,
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Movies2cTheme.colors.onBackground),
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithFade(scrollState: ScrollState, back: () -> Unit) {
    val alpha by animateFloatAsState(
        targetValue = if (scrollState.value > 100) 1f else 0f,
        label = "Toolbar Alpha"
    )

    TopAppBar(
        title = { Text("Movie Details") },
        navigationIcon = {
            IconButton(onClick = back) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Movies2cTheme.colors.background.copy(alpha = alpha)
        )
    )

    ToolbarView(
        title = stringResource(com.techadive.common.R.string.movie_details),
        startIconAction = back,
        startIconDescription = stringResource(com.techadive.common.R.string.back),
        color = Movies2cTheme.colors.background.copy(alpha = alpha),
        endIconAction = {}
    )
}
