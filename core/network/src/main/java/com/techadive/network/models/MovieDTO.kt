package com.techadive.network.models

import com.techadive.common.models.Movie

data class MovieDTO(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

fun MovieDTO.convertToMovie() =
    Movie(
        adult = this.adult,
        backdropPath = this.backdrop_path,
        genreIds = this.genre_ids,
        id = this.id,
        originalLanguage = this.original_language,
        originalTitle = this.original_title,
        overview = this.overview,
        popularity = this.popularity,
        releaseDate = this.release_date,
        title = this.title,
        video = this.video,
        voteAverage = this.vote_average,
        voteCount = this.vote_count,
        posterPath = this.poster_path,
        isFavorite = false
    )