package com.techadive.movie.usecases.movies

import com.techadive.common.utils.AppResult
import com.techadive.common.models.MovieList
import com.techadive.movie.repositories.movies.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetPopularMoviesUseCase {
    suspend fun getPopularMovies(page: Int): Flow<AppResult<MovieList>>
}

class GetPopularMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
): GetPopularMoviesUseCase {
    override suspend fun getPopularMovies(page: Int): Flow<AppResult<MovieList>> {
        return movieRepository.getPopularMovies(page)
    }
}