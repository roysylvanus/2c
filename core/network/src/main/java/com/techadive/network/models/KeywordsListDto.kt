package com.techadive.network.models

import com.techadive.common.models.KeywordsList

data class KeywordsListDto(
    val page: Int,
    val results: List<KeywordDto>,
    val total_pages: Int,
    val total_results: Int
)

fun KeywordsListDto.convertToKeywordList() =
    KeywordsList(
        page = page,
        results = results.map { it.convertToKeyword() },
        totalPages = total_pages,
        totalResults = total_results
    )