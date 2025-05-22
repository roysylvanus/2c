package com.techadive.movie.usecases.movies

import com.techadive.common.utils.AppResult
import com.techadive.common.models.MovieDetails
import com.techadive.movie.repositories.movies.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetMovieDetailsUseCase {
    suspend fun getMovieDetails(movieId: Int): Flow<AppResult<MovieDetails>>
}

class GetMovieDetailsUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMovieDetailsUseCase {
    override suspend fun getMovieDetails(movieId: Int): Flow<AppResult<MovieDetails>> {
        return movieRepository.getMovieDetails(movieId)
    }

}