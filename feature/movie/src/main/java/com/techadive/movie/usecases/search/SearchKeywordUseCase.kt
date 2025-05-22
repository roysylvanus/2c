package com.techadive.movie.usecases.search

import com.techadive.common.utils.AppResult
import com.techadive.common.models.KeywordsList
import com.techadive.movie.repositories.search.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SearchKeywordUseCase {
    suspend fun searchKeyword(
        query: String,
        page: Int
    ): Flow<AppResult<KeywordsList>>
}

class SearchKeywordUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository
): SearchKeywordUseCase {
    override suspend fun searchKeyword(
        query: String,
        page: Int
    ): Flow<AppResult<KeywordsList>> {
        return searchRepository.searchKeyword(query, page)
    }
}