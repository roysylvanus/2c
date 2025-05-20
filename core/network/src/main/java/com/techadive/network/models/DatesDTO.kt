package com.techadive.network.models

import com.techadive.common.models.Dates

data class DatesDTO(
    val maximum: String,
    val minimum: String
)

fun DatesDTO.convertToDates() =
    Dates(
        maximum = this.maximum,
        minimum = this.minimum
    )