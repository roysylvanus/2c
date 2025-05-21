package com.techadive.movie.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techadive.common.AppResult
import com.techadive.common.models.MovieList
import com.techadive.movie.usecases.GetNowPlayingMoviesUseCase
import com.techadive.movie.usecases.GetPopularMoviesUseCase
import com.techadive.movie.usecases.GetTopRatedMoviesUseCase
import com.techadive.movie.usecases.GetUpcomingMoviesUseCase
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
class HomeViewModel @Inject constructor(
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
) : ViewModel() {

    private val _homeUIState = MutableStateFlow(HomeUIState())
    val homeUIState: StateFlow<HomeUIState> get() = _homeUIState

    fun fetchHomeViewData() {
        viewModelScope.launch {
            supervisorScope {
                launch { fetchUpcomingMovies() }
                launch { fetchPopularMovies() }
                launch { fetchNowPlayingMovies() }
                launch { fetchTopRatedMovies() }
            }
        }
    }

    private suspend fun fetchUpcomingMovies() {
        withContext(Dispatchers.IO) {
            getUpcomingMoviesUseCase.getUpcomingMovies(1).collect { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _homeUIState.update {
                            it.copy(
                                isLoading = true,
                                isError = false
                            )
                        }
                    }

                    is AppResult.Error -> {
                        _homeUIState.update {
                            it.copy(
                                isLoading = false,
                                isError = true
                            )
                        }
                    }

                    is AppResult.Success -> {
                        _homeUIState.update {
                            it.copy(
                                isLoading = false,
                                isError = false,
                                upcomingMovieList = result.data
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun fetchNowPlayingMovies() {
        withContext(Dispatchers.IO) {
            getNowPlayingMoviesUseCase.getNowPlayingMovies(1).collect { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _homeUIState.update {
                            it.copy(
                                isLoading = true,
                                isError = false
                            )
                        }
                    }

                    is AppResult.Error -> {
                        _homeUIState.update {
                            it.copy(
                                isLoading = false,
                                isError = true
                            )
                        }
                    }

                    is AppResult.Success -> {
                        _homeUIState.update {
                            it.copy(
                                isLoading = false,
                                isError = false,
                                nowPlayingMovieList = result.data
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun fetchTopRatedMovies() {
        withContext(Dispatchers.IO) {
            getTopRatedMoviesUseCase.getTopRatedMovies(1).collect { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _homeUIState.update {
                            it.copy(
                                isLoading = true,
                                isError = false
                            )
                        }
                    }

                    is AppResult.Error -> {
                        _homeUIState.update {
                            it.copy(
                                isLoading = false,
                                isError = true
                            )
                        }
                    }

                    is AppResult.Success -> {
                        _homeUIState.update {
                            it.copy(
                                isLoading = false,
                                isError = false,
                                topRatedMovieList = result.data
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun fetchPopularMovies() {
        withContext(Dispatchers.IO) {
            getPopularMoviesUseCase.getPopularMovies(1).collect { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _homeUIState.update {
                            it.copy(
                                isLoading = true,
                                isError = false
                            )
                        }
                    }

                    is AppResult.Error -> {
                        _homeUIState.update {
                            it.copy(
                                isLoading = false,
                                isError = true
                            )
                        }
                    }

                    is AppResult.Success -> {
                        _homeUIState.update {
                            it.copy(
                                isLoading = false,
                                isError = false,
                                popularMovieList = result.data
                            )
                        }
                    }
                }
            }
        }
    }

    data class HomeUIState(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val upcomingMovieList: MovieList? = null,
        val topRatedMovieList: MovieList? = null,
        val popularMovieList: MovieList? = null,
        val nowPlayingMovieList: MovieList? = null
    )
}