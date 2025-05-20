package com.techadive.network.models

import com.techadive.common.models.ProductionCountry

data class ProductionCountryDTO(
    val iso_3166_1: String,
    val name: String
)

fun ProductionCountryDTO.convertToProductionCountry() =
    ProductionCountry(
        iso31661 = this.iso_3166_1,
        name = this.name
    )
