package com.techadive.network.models

import com.techadive.common.models.BelongsToCollection

data class BelongsToCollectionDTO(
    val backdrop_path: String,
    val id: Int,
    val name: String,
    val poster_path: String
)

fun BelongsToCollectionDTO.convertToBelongsToCollection() =
    BelongsToCollection(
        name = this.name,
        id = this.id,
        backdropPath = this.backdrop_path,
        posterPath = this.poster_path
    )
