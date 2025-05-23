package com.techadive.movie.ui.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.techadive.common.R
import com.techadive.common.utils.getYear
import com.techadive.common.models.MovieDetails
import com.techadive.common.utils.roundTo1Dec

@Composable
fun MovieMetadataSection(movie: MovieDetails) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconText(
            icon = R.drawable.ic_star,
            text = "${movie.voteAverage.roundTo1Dec()} / 10"
        )
        IconText(
            icon = R.drawable.ic_access_time,
            text = "${movie.runtime} min"
        )
        movie.releaseDate?.getYear()
            ?.let { IconText(icon = R.drawable.ic_calendar_today, text = it) }
    }
}