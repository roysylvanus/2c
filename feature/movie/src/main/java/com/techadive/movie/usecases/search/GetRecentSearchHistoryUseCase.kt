package com.techadive.movie.usecases.search

import com.techadive.common.utils.AppResult
import com.techadive.common.models.KeywordsList
import com.techadive.movie.repositories.search.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetRecentSearchHistoryUseCase {
    suspend fun getRecentSearchHistory(): Flow<AppResult<KeywordsList>>
}

class GetRecentSearchHistoryUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository
): GetRecentSearchHistoryUseCase {
    override suspend fun getRecentSearchHistory(): Flow<AppResult<KeywordsList>> {
        return searchRepository.getRecentSearchHistory()
    }
}