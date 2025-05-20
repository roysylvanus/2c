package com.techadive.movie.models

import com.techadive.network.models.DatesDTO

data class Dates(
    val maximum: String,
    val minimum: String
)

fun DatesDTO.convertDates() =
    Dates(
        maximum = this.maximum,
        minimum = this.minimum
    )
