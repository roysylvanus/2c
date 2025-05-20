package com.techadive.network.models

import com.techadive.common.models.MovieList

data class MovieListDTO(
    val page: Int,
    val results: List<MovieDTO>,
    val total_pages: Int,
    val total_results: Int
)

data class MovieListWithDatesDTO(
    val dates: DatesDTO,
    val page: Int,
    val results: List<MovieDTO>,
    val total_pages: Int,
    val total_results: Int
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

fun MovieListWithDatesDTO.convertToMovieList() =
    MovieList(
        dates = this.dates.convertToDates(),
        page = this.page,
        results = this.results.map {
            it.convertToMovie()
        },
        totalPages = this.total_pages,
        totalResults = this.total_results
    )