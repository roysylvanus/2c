package com.techadive.data.local.entities

import androidx.room.Entity

import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
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
    val page: Int,
    val isFavorite: Boolean
)
