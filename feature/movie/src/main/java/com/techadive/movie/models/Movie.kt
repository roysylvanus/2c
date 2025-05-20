package com.techadive.movie.models

import com.techadive.data.local.entities.MovieEntity
import com.techadive.network.models.MovieDTO

data class Movie(
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val isFavorite: Boolean
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

fun MovieEntity.convertToMovie() =
    Movie(
        adult = this.adult,
        backdropPath = this.backdropPath,
        genreIds = this.genreIds,
        id = this.id,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        releaseDate = this.releaseDate,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        posterPath = this.posterPath,
        isFavorite = isFavorite
    )
