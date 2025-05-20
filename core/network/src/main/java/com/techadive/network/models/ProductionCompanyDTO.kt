package com.techadive.network.models

import com.techadive.common.models.ProductionCompany

data class ProductionCompanyDTO(
    val id: Int,
    val logo_path: String,
    val name: String,
    val origin_country: String
)

fun ProductionCompanyDTO.convertToProductionCompany() =
    ProductionCompany(
        id = this.id,
        logoPath = this.logo_path,
        name = this.name,
        originCountry = this.origin_country
    )