package com.techadive.movie.models

import com.techadive.network.models.ProductionCountryDTO

data class ProductionCountry(
    val iso31661: String,
    val name: String
)

fun ProductionCountryDTO.convertToProductionCountry() =
    ProductionCountry(
        iso31661 = this.iso_3166_1,
        name = this.name
    )
