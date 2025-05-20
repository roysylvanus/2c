package com.techadive.movie.models

import com.techadive.network.models.SpokenLanguageDTO

data class SpokenLanguage(
    val englishName: String,
    val iso6391: String,
    val name: String
)

fun SpokenLanguageDTO.convertToSpokenLanguage() =
    SpokenLanguage(
        englishName = this.english_name,
        iso6391 = this.iso_639_1,
        name = this.name
    )