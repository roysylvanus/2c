package com.techadive.movie.viewmodels.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techadive.common.AppResult
import com.techadive.common.models.MovieCardData
import com.techadive.common.models.MovieDetails
import com.techadive.common.models.MovieList
import com.techadive.common.toBooleanStrict
import com.techadive.movie.usecases.favorites.AddToFavoritesUseCase
import com.techadive.movie.usecases.favorites.CheckIfMovieIsFavoriteUseCase
import com.techadive.movie.usecases.favorites.RemoveFavoriteUseCase
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
    private val checkIfMovieIsFavoriteUseCase: CheckIfMovieIsFavoriteUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
) : ViewModel() {

    private val _movieDetailsUIState = MutableStateFlow(MovieDetailsUIState())
    val movieDetailsUIState: StateFlow<MovieDetailsUIState> get() = _movieDetailsUIState

    fun onEvent(event: MovieDetailsEvent) {
        when (event) {
            is MovieDetailsEvent.FetchMovieDetail -> {
                fetchMovieData(event.movieId)
            }

            is MovieDetailsEvent.UpdateFavoriteStatus -> {
                updateFavoriteStatus(event.movieId, event.isFavorite)
            }
        }
    }

    private suspend fun fetchMovieDetails(movieId: Int) {
        withContext(Dispatchers.IO) {
            getMovieDetailsUseCase.getMovieDetails(movieId).collect { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _movieDetailsUIState.update {
                            it.copy(
                                isError = false,
                                isLoading = true
                            )
                        }

                    }

                    is AppResult.Error -> {
                        _movieDetailsUIState.update {
                            it.copy(
                                isLoading = false,
                                isError = true
                            )
                        }
                    }

                    is AppResult.Success -> {
                        _movieDetailsUIState.update {
                            it.copy(
                                movieDetails = result.data,
                                isError = false,
                                isLoading = false
                            )
                        }

                        checkIfMovieIsFavorite(movieId)
                    }
                }
            }
        }
    }

    private fun fetchMovieData(movieId: Int?) {
        viewModelScope.launch {
            supervisorScope {
                if (movieId != null) {
                    fetchMovieDetails(movieId)
                    fetchRecommendedMovies(movieId)
                } else {
                    _movieDetailsUIState.update {
                        it.copy(
                            isError = true
                        )
                    }
                }
            }
        }
    }

    private suspend fun fetchRecommendedMovies(movieId: Int) {
        withContext(Dispatchers.IO) {
            getRecommendedMoviesUseCase.getRecommendedMovies(movieId = movieId, page = 1)
                .collect { result ->
                    when (result) {
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

    private fun checkIfMovieIsFavorite(movieId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val isFavorite = checkIfMovieIsFavoriteUseCase.isFavorite(movieId)

                _movieDetailsUIState.update {
                    it.copy(
                        movieDetails = _movieDetailsUIState.value.movieDetails?.copy(
                            isFavorite = isFavorite.toBooleanStrict()
                        )
                    )
                }
            }
        }
    }

    private fun updateFavoriteStatus(movieId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (isFavorite) {
                    val movie = _movieDetailsUIState.value.movieDetails
                    movie?.let {
                        addToFavoritesUseCase.addToFavorites(
                            MovieCardData(
                                movieId = it.id,
                                releaseDate = movie.releaseDate,
                                originalTitle = movie.originalTitle,
                                voteAverage = movie.voteAverage,
                                posterPath = movie.posterPath,
                                isFavorite = movie.isFavorite
                            )
                        )
                    }
                } else {
                    removeFavoriteUseCase.removeFromFavorites(movieId)
                }
                checkIfMovieIsFavorite(movieId)
            }
        }
    }

    sealed class MovieDetailsEvent {
        data class FetchMovieDetail(val movieId: Int?) : MovieDetailsEvent()
        data class UpdateFavoriteStatus(val movieId: Int, val isFavorite: Boolean) :
            MovieDetailsEvent()
    }

    data class MovieDetailsUIState(
        val movieDetails: MovieDetails? = null,
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val movieList: MovieList? = null,
    )
}