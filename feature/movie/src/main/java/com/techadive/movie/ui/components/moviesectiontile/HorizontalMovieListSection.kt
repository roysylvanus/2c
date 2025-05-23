package com.techadive.movie.ui.components.moviesectiontile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.techadive.common.models.Movie
import com.techadive.common.models.convertToMovieCardData
import com.techadive.movie.ui.components.MovieCard

@Composable
fun HorizontalMovieListSection(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    showDetails: (Int) -> Unit
) {
    val moviesWithPosters = movies.filter { it.posterPath != null && it.backdropPath != null }

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(moviesWithPosters.take(10)) { movie ->
            movie.posterPath?.let {
                MovieCard(
                    movie.convertToMovieCardData(),
                    showDetails = showDetails
                )
            }
        }
    }
}