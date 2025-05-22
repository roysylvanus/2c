package com.techadive.movie.usecases.movies

import com.techadive.common.utils.AppResult
import com.techadive.common.models.MovieList
import com.techadive.movie.repositories.movies.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetRecommendedMoviesUseCase {
    suspend fun getRecommendedMovies(movieId: Int, page: Int): Flow<AppResult<MovieList>>
}

class GetRecommendedMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
): GetRecommendedMoviesUseCase {
    override suspend fun getRecommendedMovies(
        movieId: Int,
        page: Int
    ): Flow<AppResult<MovieList>> {
        return movieRepository.getRecommendedMovies(movieId = movieId, page = page)
    }

}