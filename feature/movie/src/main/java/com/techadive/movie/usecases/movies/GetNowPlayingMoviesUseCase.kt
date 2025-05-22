package com.techadive.movie.usecases.movies

import com.techadive.common.utils.AppResult
import com.techadive.common.models.MovieList
import com.techadive.movie.repositories.movies.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetNowPlayingMoviesUseCase {
    suspend fun getNowPlayingMovies(page: Int): Flow<AppResult<MovieList>>
}

class GetNowPlayingMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
): GetNowPlayingMoviesUseCase {
    override suspend fun getNowPlayingMovies(page: Int): Flow<AppResult<MovieList>> {
        return movieRepository.getNowPlayingMovies(page)
    }
}