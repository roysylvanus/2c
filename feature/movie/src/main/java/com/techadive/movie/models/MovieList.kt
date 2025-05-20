package com.techadive.movie.models

import com.techadive.network.models.MovieListDTO
import com.techadive.network.models.MovieListWithDatesDTO

data class MovieList(
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)

data class MovieListWithDates(
    val dates: Dates,
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)

fun MovieListDTO.convertToMovieList() =
    MovieList(
        page = this.page,
        results = this.results.map {
            it.convertToMovie()
        },
        totalPages = this.total_pages,
        totalResults = this.total_results
    )

fun MovieListWithDatesDTO.convertToMovieListWithDates() =
    MovieListWithDates(
        dates = this.dates.convertDates(),
        page = this.page,
        results = this.results.map {
            it.convertToMovie()
        },
        totalPages = this.total_pages,
        totalResults = this.total_results
    )
