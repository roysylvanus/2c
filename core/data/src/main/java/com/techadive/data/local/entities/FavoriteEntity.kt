package com.techadive.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.techadive.common.models.MovieCardData

@Entity("favorites")
data class FavoriteEntity(
    @PrimaryKey
    val movieId: Int,
    val releaseDate: String?,
    val originalTitle: String?,
    val voteAverage: Double,
    val posterPath: String?,
)

fun FavoriteEntity.convertToFavorite() =
    MovieCardData(
        movieId = movieId,
        releaseDate = releaseDate,
        originalTitle = originalTitle,
        voteAverage = voteAverage,
        posterPath = posterPath,
        isFavorite = true
    )
