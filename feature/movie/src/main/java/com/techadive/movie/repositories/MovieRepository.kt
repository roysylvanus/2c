package com.techadive.movie.repositories

import com.techadive.movie.models.Movie
import com.techadive.movie.models.MovieDetails
import com.techadive.network.api.ApiService
import com.techadive.utils.LanguageProvider
import com.techadive.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MovieRepository {
    suspend fun getNowPlayingMovies(page: Int): Flow<Resource<List<Movie>>>
    suspend fun getPopularMovies(page: Int): Flow<Resource<List<Movie>>>
    suspend fun getTopRatedMovies(page: Int): Flow<Resource<List<Movie>>>
    suspend fun getUpcomingMovies(page: Int): Flow<Resource<List<Movie>>>
    suspend fun searchMovies(query: String, page: Int): Flow<Resource<List<Movie>>>
    suspend fun getMovieDetails(movieId: String): Flow<Resource<MovieDetails>>
}

class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val languageProvider: LanguageProvider
): MovieRepository {
    override suspend fun getNowPlayingMovies(page: Int): Flow<Resource<List<Movie>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPopularMovies(page: Int): Flow<Resource<List<Movie>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTopRatedMovies(page: Int): Flow<Resource<List<Movie>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUpcomingMovies(page: Int): Flow<Resource<List<Movie>>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchMovies(
        query: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetails(movieId: String): Flow<Resource<MovieDetails>> {
        TODO("Not yet implemented")
    }

}