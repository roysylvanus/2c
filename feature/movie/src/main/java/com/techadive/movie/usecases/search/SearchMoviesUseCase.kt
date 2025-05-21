package com.techadive.movie.usecases.search

import com.techadive.common.AppResult
import com.techadive.common.models.MovieList
import com.techadive.movie.repositories.MovieRepository
import com.techadive.movie.repositories.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SearchMoviesUseCase {
    suspend fun searchMovies(
        query: String,
        includeAdult: Boolean,
        page: Int
    ): Flow<AppResult<MovieList>>
}

class SearchMoviesUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository
): SearchMoviesUseCase {
    override suspend fun searchMovies(
        query: String,
        includeAdult: Boolean,
        page: Int
    ): Flow<AppResult<MovieList>> {
        return searchRepository.searchMovies(
            query,
            includeAdult,
            page
        )
    }

}