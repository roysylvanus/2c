package com.techadive.movie.repositories.movies

import com.techadive.data.local.dao.MovieDao
import com.techadive.common.models.MovieDetails
import com.techadive.network.api.ApiService
import com.techadive.common.providers.LanguageProvider
import com.techadive.common.utils.AppResult
import com.techadive.common.models.MovieList
import com.techadive.data.local.entities.convertToMovieList
import com.techadive.data.local.entities.convertToMovieListEntity
import com.techadive.movie.utils.MovieListCategory
import com.techadive.network.models.convertToMovieDetails
import com.techadive.network.models.convertToMovieList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Repository implementation for fetching movies and movie details from
 * [ApiService] with caching support via [MovieDao].
 *
 * Handles network errors gracefully by returning cached data when available,
 * emitting loading and error states wrapped in [AppResult].
 */

interface MovieRepository {
    suspend fun getNowPlayingMovies(page: Int): Flow<AppResult<MovieList>>
    suspend fun getPopularMovies(page: Int): Flow<AppResult<MovieList>>
    suspend fun getTopRatedMovies(page: Int): Flow<AppResult<MovieList>>
    suspend fun getUpcomingMovies(page: Int): Flow<AppResult<MovieList>>
    suspend fun getMovieDetails(movieId: Int): Flow<AppResult<MovieDetails>>
    suspend fun getRecommendedMovies(movieId: Int, page: Int): Flow<AppResult<MovieList>>
}

class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val languageProvider: LanguageProvider,
    private val movieDao: MovieDao
) : MovieRepository {

    /**
     * Fetches "Now Playing, Upcoming, Top Rated and Popular" movies from the API, caches them, and falls back to cache on error.
     */
    override suspend fun getNowPlayingMovies(page: Int): Flow<AppResult<MovieList>> {
        return flow {
            emit(AppResult.Loading)

            try {
                val movieListWithDatesDto = apiService.getNowPlayingMovies(
                    language = languageProvider.getLanguage(),
                    page = page
                )

                val movieList = movieListWithDatesDto.convertToMovieList().copy(
                    category = MovieListCategory.NOW_PLAYING.value,
                )

                movieDao.insertMovieList(movieList.convertToMovieListEntity())

                emit(AppResult.Success(movieList))
            } catch (e: Exception) {

                val cachedMovieList =
                    movieDao.getMovieListByCategory(MovieListCategory.NOW_PLAYING.value)

                if (cachedMovieList != null) {
                    emit(AppResult.Success(cachedMovieList.convertToMovieList()))
                    emit(AppResult.Error(e))
                } else {
                    emit(AppResult.Error(e))
                }
            }
        }
    }

    override suspend fun getPopularMovies(page: Int): Flow<AppResult<MovieList>> {
        return flow {
            emit(AppResult.Loading)

            try {
                val movieListDto = apiService.getPopularMovies(
                    language = languageProvider.getLanguage(),
                    page = page
                )

                val movieList = movieListDto.convertToMovieList().copy(
                    category = MovieListCategory.POPULAR.value,
                )

                movieDao.insertMovieList(movieList.convertToMovieListEntity())

                emit(AppResult.Success(movieList))
            } catch (e: Exception) {

                val cachedMovieList =
                    movieDao.getMovieListByCategory(MovieListCategory.POPULAR.value)

                if (cachedMovieList != null) {
                    emit(AppResult.Success(cachedMovieList.convertToMovieList()))
                    emit(AppResult.Error(e))
                } else {
                    emit(AppResult.Error(e))
                }
            }
        }
    }

    override suspend fun getTopRatedMovies(page: Int): Flow<AppResult<MovieList>> {
        return flow {
            emit(AppResult.Loading)

            try {
                val movieListDto = apiService.getTopRatedMovies(
                    language = languageProvider.getLanguage(),
                    page = page
                )

                val movieList = movieListDto.convertToMovieList().copy(
                    category = MovieListCategory.TOP_RATED.value,
                )

                movieDao.insertMovieList(movieList.convertToMovieListEntity())

                emit(AppResult.Success(movieList))
            } catch (e: Exception) {

                val cachedMovieList =
                    movieDao.getMovieListByCategory(MovieListCategory.TOP_RATED.value)

                if (cachedMovieList != null) {
                    emit(AppResult.Success(cachedMovieList.convertToMovieList()))
                    emit(AppResult.Error(e))
                } else {
                    emit(AppResult.Error(e))
                }
            }
        }
    }

    override suspend fun getUpcomingMovies(page: Int): Flow<AppResult<MovieList>> {
        return flow {
            emit(AppResult.Loading)

            try {
                val movieListWithDatesDto = apiService.getUpcomingMovies(
                    language = languageProvider.getLanguage(),
                    page = page
                )

                val movieList = movieListWithDatesDto.convertToMovieList().copy(
                    category = MovieListCategory.UPCOMING.value,
                )

                movieDao.insertMovieList(movieList.convertToMovieListEntity())

                emit(AppResult.Success(movieList))
            } catch (e: Exception) {

                val cachedMovieList =
                    movieDao.getMovieListByCategory(MovieListCategory.UPCOMING.value)

                if (cachedMovieList != null) {
                    emit(AppResult.Success(cachedMovieList.convertToMovieList()))
                    emit(AppResult.Error(e))
                } else {
                    emit(AppResult.Error(e))
                }
            }
        }
    }
    /**
     * Fetches recommended movies by movie ID.
     */
    override suspend fun getRecommendedMovies(movieId: Int, page: Int): Flow<AppResult<MovieList>> {
        return flow {
            emit(AppResult.Loading)

            try {
                val movieListWithDatesDto = apiService.getRecommendedMovies(
                    movieId = movieId,
                    language = languageProvider.getLanguage(),
                    page = page
                )

                val movieList = movieListWithDatesDto.convertToMovieList()
                emit(AppResult.Success(movieList))
            } catch (e: Exception) {
                emit(AppResult.Error(e))

            }
        }
    }

    /**
     * Fetches detailed info for a movie by ID.
     */
    override suspend fun getMovieDetails(movieId: Int): Flow<AppResult<MovieDetails>> {
        return flow {
            emit(AppResult.Loading)

            try {
                val movieDetails = apiService.getMovieDetails(
                    movieId = movieId,
                    language = languageProvider.getLanguage(),
                ).convertToMovieDetails()

                emit(AppResult.Success(movieDetails))
            } catch (e: Exception) {
                emit(AppResult.Error(e))
            }
        }
    }

}