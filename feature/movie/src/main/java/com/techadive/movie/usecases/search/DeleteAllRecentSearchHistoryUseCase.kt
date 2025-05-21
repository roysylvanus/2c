package com.techadive.movie.usecases.search

import com.techadive.movie.repositories.SearchRepository
import javax.inject.Inject

interface DeleteAllRecentSearchHistoryUseCase {
    suspend fun deleteAllRecentSearchHistory()
}

class DeleteAllRecentSearchHistoryUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository
) : DeleteAllRecentSearchHistoryUseCase {
    override suspend fun deleteAllRecentSearchHistory() {
        return searchRepository.deleteAllRecentSearchHistory()
    }

}