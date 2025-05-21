package com.techadive.movie.usecases.movies

import com.techadive.common.AppResult
import com.techadive.common.models.MovieList
import com.techadive.movie.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetTopRatedMoviesUseCase {
    suspend fun getTopRatedMovies(page: Int): Flow<AppResult<MovieList>>
}

class GetTopRatedMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
): GetTopRatedMoviesUseCase {
    override suspend fun getTopRatedMovies(page: Int): Flow<AppResult<MovieList>> {
        return movieRepository.getTopRatedMovies(page)
    }
}