package com.techadive.movie.usecases.movies

import com.techadive.common.utils.AppResult
import com.techadive.common.models.MovieList
import com.techadive.movie.repositories.movies.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetUpcomingMoviesUseCase {
    suspend fun getUpcomingMovies(page: Int): Flow<AppResult<MovieList>>
}

class GetUpcomingMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
): GetUpcomingMoviesUseCase {
    override suspend fun getUpcomingMovies(page: Int): Flow<AppResult<MovieList>> {
        return movieRepository.getUpcomingMovies(page)
    }

}