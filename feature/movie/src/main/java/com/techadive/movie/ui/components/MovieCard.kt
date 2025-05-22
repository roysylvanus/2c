package com.techadive.movie.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.techadive.common.R
import com.techadive.common.getYear
import com.techadive.common.models.MovieCardData
import com.techadive.common.roundTo2Dec
import com.techadive.designsystem.components.AppImageView
import com.techadive.designsystem.theme.Movies2cTheme
import com.techadive.designsystem.theme.Palette
import com.techadive.network.utils.ApiUtils

@Composable
fun MovieCard(
    movie: MovieCardData,
    showDetails: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .width(150.dp),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clickable {
                    showDetails(
                        movie.movieId
                    )
                },
            elevation = CardDefaults.cardElevation()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.4f)),
                            startY = 200f
                        )
                    )
            ) {
                AppImageView(
                    url = ApiUtils.IMAGE_URL + movie.posterPath,
                    contentDescription = movie.originalTitle,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Movies2cTheme.colors.background.copy(alpha = 0.75f)) // then background
                            .padding(horizontal = 6.dp, vertical = 2.dp), // padding inside bg
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = movie.voteAverage.roundTo2Dec().toString(),
                            style = Movies2cTheme.typography.body5,
                            color = Movies2cTheme.colors.onBackground
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Icon(
                            painter = painterResource(R.drawable.ic_star),
                            tint = Palette.orange,
                            contentDescription = "star",
                            modifier = Modifier.size(10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    if (!movie.releaseDate.isNullOrEmpty()) {
                        Text(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Movies2cTheme.colors.background.copy(alpha = 0.75f)) // then background
                                .padding(horizontal = 6.dp, vertical = 2.dp),
                            text = movie.releaseDate!!.getYear(),
                            style = Movies2cTheme.typography.body5,
                            color = Movies2cTheme.colors.onBackground
                        )
                    }
                }

                Icon(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(5.dp),
                    imageVector = if (movie.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.favorites),
                    tint = if (movie.isFavorite) Palette.red else
                        Movies2cTheme.colors.onBackground
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = movie.originalTitle.orEmpty(),
            style = Movies2cTheme.typography.h5,
            color = Movies2cTheme.colors.onBackground,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (!movie.overview.isNullOrEmpty()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                text = movie.overview!!,
                style = Movies2cTheme.typography.body5,
                color = Movies2cTheme.colors.onBackground.copy(alpha = 0.7f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
            )
        }
    }
}