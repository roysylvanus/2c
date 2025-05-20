package com.techadive.movie.models

import com.techadive.network.models.GenreDTO

data class Genre(
    val id: Int,
    val name: String
)

fun GenreDTO.convertToGenre() =
    Genre(
        id = this.id,
        name = this.name
    )