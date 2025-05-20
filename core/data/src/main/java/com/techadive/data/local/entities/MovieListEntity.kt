package com.techadive.data.local.entities

import androidx.room.Entity

import androidx.room.PrimaryKey
import com.techadive.common.models.Dates
import com.techadive.common.models.Movie
import com.techadive.common.models.MovieList

@Entity(tableName = "movie_lists")
data class MovieListEntity(
    @PrimaryKey val category: String,
    val dates: Dates?,
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)

fun MovieListEntity.convertToMovieList() =
    MovieList(
        dates = this.dates,
        page = this.page,
        results = this.results,
        totalPages = this.totalPages,
        totalResults = this.totalResults
    )

fun MovieList.convertToMovieListEntity() =
    MovieListEntity(
        category = "",
        dates = this.dates,
        page = this.page,
        results = this.results,
        totalPages = this.totalPages,
        totalResults = this.totalResults
    )