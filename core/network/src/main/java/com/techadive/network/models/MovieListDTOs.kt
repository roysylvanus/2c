package com.techadive.network.models

data class MovieListDTOs(
    val page: Int,
    val results: List<MovieResult>,
    val total_pages: Int,
    val total_results: Int
)

data class MovieListWithDatesDTOs(
    val dates: MovieDates,
    val page: Int,
    val results: List<MovieResult>,
    val total_pages: Int,
    val total_results: Int
)