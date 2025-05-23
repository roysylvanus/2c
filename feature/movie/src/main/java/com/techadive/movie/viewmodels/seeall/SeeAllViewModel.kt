package com.techadive.movie.viewmodels.seeall

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techadive.common.utils.AppResult
import com.techadive.common.models.MovieList
import com.techadive.movie.usecases.favorites.GetFavoritesUseCase
import com.techadive.movie.usecases.movies.GetPopularMoviesUseCase
import com.techadive.movie.usecases.movies.GetRecommendedMoviesUseCase
import com.techadive.movie.usecases.movies.GetTopRatedMoviesUseCase
import com.techadive.movie.usecases.movies.GetUpcomingMoviesUseCase
import com.techadive.movie.utils.MovieListCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SeeAllViewModel @Inject constructor(
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getRecommendedMoviesUseCase: GetRecommendedMoviesUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
) : ViewModel() {

    private val _seeAllUIState = MutableStateFlow(SeeAllUIState())
    val seeAllUIState: StateFlow<SeeAllUIState> get() = _seeAllUIState

    private var favorites = emptyList<Int>()

    private suspend fun fetchFavorites() {
        withContext(Dispatchers.IO) {
            favorites = getFavoritesUseCase.getFavorites().map { it.movieId }
        }
    }

    private suspend fun fetchUpcomingMovies(page: Int = 1) {
        withContext(Dispatchers.IO) {
            getUpcomingMoviesUseCase.getUpcomingMovies(page).collect { result ->
                handleMovieFetchResult(result, page)
            }
        }
    }

    private suspend fun fetchTopRatedMovies(page: Int = 1) {
        withContext(Dispatchers.IO) {
            getTopRatedMoviesUseCase.getTopRatedMovies(page).collect { result ->
                handleMovieFetchResult(result, page)
            }
        }
    }

    private suspend fun fetchPopularMovies(page: Int = 1) {
        withContext(Dispatchers.IO) {
            getPopularMoviesUseCase.getPopularMovies(page).collect { result ->
                handleMovieFetchResult(result, page)
            }
        }
    }

    private suspend fun fetchRecommendedMovies(movieId: Int, page: Int = 1) {
        withContext(Dispatchers.IO) {
            getRecommendedMoviesUseCase.getRecommendedMovies(movieId = movieId, page = page)
                .collect { result ->
                    handleMovieFetchResult(result, page)
                }
        }
    }

    fun fetchListByCategory(movieListCategory: MovieListCategory, page: Int, extra: Int? = null) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                fetchFavorites()

                _seeAllUIState.update {
                    it.copy(
                        isError = false
                    )
                }

                when(movieListCategory) {
                    MovieListCategory.UPCOMING -> {
                        fetchUpcomingMovies(page)
                    }
                    MovieListCategory.TOP_RATED -> {
                        fetchTopRatedMovies(page)
                    }
                    MovieListCategory.POPULAR -> {
                        fetchPopularMovies(page)
                    }
                    MovieListCategory.RECOMMENDED -> {
                        if (extra != null) {
                            fetchRecommendedMovies(extra, page)
                        } else {

                        }
                    }
                    else -> Unit
                }
            }
        }
    }
    private fun handleMovieFetchResult(
        result: AppResult<MovieList>,
        page: Int
    ) {
        when (result) {
            is AppResult.Loading -> {
                _seeAllUIState.update {
                    it.copy(
                        isLoading = true,
                        isError = false
                    )
                }
            }

            is AppResult.Error -> {
                Log.e("seeall", result.error.message.toString())
                _seeAllUIState.update {
                    it.copy(
                        isLoading = false,
                        isError = true
                    )
                }
            }

            is AppResult.Success -> {
                val newMovies = result.data.results.map {
                    it.copy(isFavorite = favorites.contains(it.id))
                }

                _seeAllUIState.update { currentState ->
                    val combined = (currentState.movieList?.results.orEmpty() + newMovies)
                        .distinctBy { it.id }
                        .takeLast(500)

                    currentState.copy(
                        isLoading = false,
                        isError = false,
                        movieList = result.data.copy(results = combined, page = page),
                    )
                }
            }
        }
    }

    fun clearList() {
        _seeAllUIState.update {
            it.copy(
                movieList = null
            )
        }
    }

    data class SeeAllUIState(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val movieList: MovieList? = null,
    )

}