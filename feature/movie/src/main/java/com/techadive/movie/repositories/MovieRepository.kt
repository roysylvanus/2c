package com.techadive.movie.repositories

import com.techadive.network.api.ApiService
import com.techadive.network.models.MovieDetailsDTO
import com.techadive.network.models.MovieResult
import com.techadive.utils.LanguageProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MovieRepository {
    suspend fun getNowPlayingMovies(page: Int): Flow<List<MovieResult>>
    suspend fun getPopularMovies(page: Int): Flow<List<MovieResult>>
    suspend fun getTopRatedMovies(page: Int): Flow<List<MovieResult>>
    suspend fun getUpcomingMovies(page: Int): Flow<List<MovieResult>>
    suspend fun searchMovies(query: String, page: Int): Flow<List<MovieResult>>
    suspend fun getMovieDetails(movieId: String): Flow<MovieDetailsDTO>
}

class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val languageProvider: LanguageProvider
): MovieRepository {
    override suspend fun getNowPlayingMovies(page: Int): Flow<List<MovieResult>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPopularMovies(page: Int): Flow<List<MovieResult>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTopRatedMovies(page: Int): Flow<List<MovieResult>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUpcomingMovies(page: Int): Flow<List<MovieResult>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchMovies(
        query: String,
        page: Int
    ): Flow<List<MovieResult>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetails(movieId: String): Flow<MovieDetailsDTO> {
        TODO("Not yet implemented")
    }

}