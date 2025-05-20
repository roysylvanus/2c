package com.techadive.network.models

import com.techadive.common.models.Genre

data class GenreDTO(
    val id: Int,
    val name: String
)

fun GenreDTO.convertToGenre() =
    Genre(
        id = this.id,
        name = this.name
    )