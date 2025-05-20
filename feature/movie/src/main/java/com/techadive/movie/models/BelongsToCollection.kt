package com.techadive.movie.models

import com.techadive.network.models.BelongsToCollectionDTO

data class BelongsToCollection(
    val backdropPath: String,
    val id: Int,
    val name: String,
    val posterPath: String
)

fun BelongsToCollectionDTO.convertToBelongsToCollection() =
    BelongsToCollection(
        name = this.name,
        id = this.id,
        backdropPath = this.backdrop_path,
        posterPath = this.poster_path
    )
