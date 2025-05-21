package com.techadive.common.models

data class KeywordsList(
    val page: Int,
    val results: List<Keyword>,
    val totalPages: Int,
    val totalResults: Int
)