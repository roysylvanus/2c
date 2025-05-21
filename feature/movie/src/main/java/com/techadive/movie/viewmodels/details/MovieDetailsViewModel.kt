package com.techadive.movie.viewmodels.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techadive.common.AppResult
import com.techadive.common.models.MovieDetails
import com.techadive.common.models.MovieList
import com.techadive.movie.usecases.movies.GetMovieDetailsUseCase
import com.techadive.movie.usecases.movies.GetRecommendedMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getRecommendedMoviesUseCase: GetRecommendedMoviesUseCase,
): ViewModel() {

    private val _movieDetailsUIState = MutableStateFlow(MovieDetailsUIState())
    val movieDetailsUIState: StateFlow<MovieDetailsUIState> get() = _movieDetailsUIState

    fun onEvent(event: MovieDetailsEvent) {
        when(event) {
            is MovieDetailsEvent.FetchMovieDetail -> {
                fetchMovieData(event.movieId)
            }
            is MovieDetailsEvent.UpdateFavoriteStatus -> {

            }
        }
    }

    private suspend fun fetchMovieDetails(movieId: Int) {
        withContext(Dispatchers.IO) {
            getMovieDetailsUseCase.getMovieDetails(movieId).collect { result ->
                when(result) {
                    is AppResult.Loading -> {
                        _movieDetailsUIState.update {
                            it.copy(
                                isError = false,
                                isLoading = true
                            )
                        }

                    }
                    is AppResult.Error -> {
                        Log.i("_movieDetailsUIState", result.error.toString())

                        _movieDetailsUIState.update {
                            it.copy(
                                isLoading = false,
                                isError = true
                            )
                        }
                    }
                    is AppResult.Success -> {
                        Log.i("_movieDetailsUIState", result.data.toString())
                        _movieDetailsUIState.update {
                            it.copy(
                                movieDetails = result.data,
                                isError = false,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun fetchMovieData(movieId: Int?) {
        viewModelScope.launch {
            supervisorScope {
                if (movieId !=null) {
                    fetchMovieDetails(movieId)
                    fetchRecommendedMovies(movieId)
                } else {
                    TODO()
                }
            }
        }
    }

    private suspend fun fetchRecommendedMovies(movieId: Int) {
        withContext(Dispatchers.IO) {
            getRecommendedMoviesUseCase.getRecommendedMovies(movieId = movieId, page = 1)
                .collect { result ->
                    when(result) {
                        is AppResult.Error -> {
                            Log.e("fetchRecommendedMovies", result.error.toString())
                        }
                        is AppResult.Success -> {
                            _movieDetailsUIState.update {
                                it.copy(
                                    movieList = result.data
                                )
                            }
                        }
                        else -> Unit
                    }
                }
        }
    }

    private fun updateFavoriteStatus(movieId: String, isFavorite: Boolean) {

    }

    sealed class MovieDetailsEvent {
        data class FetchMovieDetail(val movieId: Int?): MovieDetailsEvent()
        data class UpdateFavoriteStatus(val movieId: Int, val isFavorite: Boolean) : MovieDetailsEvent()
    }

    data class MovieDetailsUIState(
        val movieDetails: MovieDetails? = null,
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val movieList: MovieList? = null,
    )
}