package com.techadive.common.models

data class MovieCardData(
    val movieId: Int,
    val releaseDate: String?,
    val originalTitle: String?,
    val voteAverage: Double,
    val posterPath: String?,
    val isFavorite: Boolean
)

fun Movie.convertToMovieCardData() =
    MovieCardData(
        movieId = id,
        releaseDate = releaseDate,
        originalTitle = originalTitle,
        voteAverage = voteAverage,
        posterPath = posterPath,
        isFavorite = isFavorite
    )
