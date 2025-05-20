package com.techadive.movie.usecases

import com.techadive.common.AppResult
import com.techadive.common.models.MovieList
import com.techadive.movie.repositories.MovieRepository
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
    private val movieRepository: MovieRepository
): SearchMoviesUseCase {
    override suspend fun searchMovies(
        query: String,
        includeAdult: Boolean,
        page: Int
    ): Flow<AppResult<MovieList>> {
        return movieRepository.searchMovies(
            query,
            includeAdult,
            page
        )
    }

}