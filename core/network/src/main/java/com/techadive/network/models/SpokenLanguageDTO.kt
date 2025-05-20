package com.techadive.network.models

import com.techadive.common.models.SpokenLanguage

data class SpokenLanguageDTO(
    val english_name: String,
    val iso_639_1: String,
    val name: String
)

fun SpokenLanguageDTO.convertToSpokenLanguage() =
    SpokenLanguage(
        englishName = this.english_name,
        iso6391 = this.iso_639_1,
        name = this.name
    )