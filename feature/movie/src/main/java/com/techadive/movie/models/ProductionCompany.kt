package com.techadive.movie.models

import com.techadive.network.models.ProductionCompanyDTO

data class ProductionCompany(
    val id: Int,
    val logoPath: String,
    val name: String,
    val originCountry: String
)

fun ProductionCompanyDTO.convertToProductionCompany() =
    ProductionCompany(
        id = this.id,
        logoPath = this.logo_path,
        name = this.name,
        originCountry = this.origin_country
    )
