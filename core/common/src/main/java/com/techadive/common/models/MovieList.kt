package com.techadive.common.models

data class MovieList(
    val category: String? = null,
    val dates: Dates? = null,
    val page: Int = 0,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)