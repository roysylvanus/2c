package com.techadive.network.models

import com.techadive.common.models.Keyword

data class KeywordDto(
    val id: Int,
    val name: String
)

fun KeywordDto.convertToKeyword() =
    Keyword(
        id = id,
        name = name
    )