package com.techadive.movie.viewmodels.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techadive.common.utils.AppResult
import com.techadive.common.models.MovieList
import com.techadive.movie.usecases.favorites.GetFavoritesUseCase
import com.techadive.movie.usecases.movies.GetNowPlayingMoviesUseCase
import com.techadive.movie.usecases.movies.GetPopularMoviesUseCase
import com.techadive.movie.usecases.movies.GetTopRatedMoviesUseCase
import com.techadive.movie.usecases.movies.GetUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
) : ViewModel() {

    private val _homeUIState = MutableStateFlow(HomeUIState())
    val homeUIState: StateFlow<HomeUIState> = _homeUIState

    private var favorites = emptyList<Int>()

    fun fetchHomeViewData(isRefresh: Boolean = false) {
        viewModelScope.launch {
            if (isRefresh) {
                _homeUIState.update { it.copy(isRefreshing = true) }
            }
            supervisorScope {
                launch { fetchFavorites() }
                launch { fetchUpcomingMovies() }
                launch { fetchNowPlayingMovies() }
                launch { fetchPopularMovies() }
                launch { fetchTopRatedMovies() }
            }
            if (isRefresh) {
                _homeUIState.update { it.copy(isRefreshing = false) }
            }
        }
    }

    private suspend fun fetchFavorites() {
        withContext(Dispatchers.IO) {
            favorites = getFavoritesUseCase.getFavorites().map { it.movieId }
        }
    }

    private suspend fun fetchUpcomingMovies(page: Int = 1) {
        fetchSection(
            fetch = { getUpcomingMoviesUseCase.getUpcomingMovies(page) },
            onSuccess = { movieList ->
                movieList.copy(results = movieList.results.map {
                    it.copy(isFavorite = favorites.contains(it.id))
                })
            },
            updateState = { updated ->
                this.copy(upcomingState = updated)
            }
        )
    }

    private suspend fun fetchNowPlayingMovies(page: Int = 1) {
        fetchSection(
            fetch = { getNowPlayingMoviesUseCase.getNowPlayingMovies(page) },
            onSuccess = { it },
            updateState = { updated ->
                this.copy(nowPlayingState = updated)
            }
        )
    }

    private suspend fun fetchPopularMovies(page: Int = 1) {
        fetchSection(
            fetch = { getPopularMoviesUseCase.getPopularMovies(page) },
            onSuccess = { movieList ->
                movieList.copy(results = movieList.results.map {
                    it.copy(isFavorite = favorites.contains(it.id))
                })
            },
            updateState = { updated ->
                this.copy(popularState = updated)
            }
        )
    }

    private suspend fun fetchTopRatedMovies(page: Int = 1) {
        fetchSection(
            fetch = { getTopRatedMoviesUseCase.getTopRatedMovies(page) },
            onSuccess = { movieList ->
                movieList.copy(results = movieList.results.map {
                    it.copy(isFavorite = favorites.contains(it.id))
                })
            },
            updateState = { updated ->
                this.copy(topRatedState = updated)
            }
        )
    }

    private suspend fun <T> fetchSection(
        fetch: suspend () -> Flow<AppResult<T>>,
        onSuccess: (T) -> T,
        updateState: (HomeUIState.(SectionState<T>) -> HomeUIState)
    ) {
        withContext(Dispatchers.IO) {
            var cachedData: T? = null
            fetch().collect { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _homeUIState.update { it.updateState(SectionState(isLoading = true, data = cachedData)) }
                    }
                    is AppResult.Success -> {
                        val transformedData = onSuccess(result.data)
                        cachedData = transformedData
                        _homeUIState.update { it.updateState(SectionState(isLoading = false, isError = false, data = transformedData)) }
                    }
                    is AppResult.Error -> {
                        // Keep cached data but mark error
                        _homeUIState.update { it.updateState(SectionState(isLoading = false, isError = true, data = cachedData)) }
                    }
                }
            }
        }
    }


    data class HomeUIState(
        val isRefreshing: Boolean = false,
        val upcomingState: SectionState<MovieList> = SectionState(),
        val topRatedState: SectionState<MovieList> = SectionState(),
        val popularState: SectionState<MovieList> = SectionState(),
        val nowPlayingState: SectionState<MovieList> = SectionState(),
    ) {
        val allSectionsFailed: Boolean
            get() = listOf(upcomingState, topRatedState, popularState, nowPlayingState).all { it.isError }
        val allSectionsLoading: Boolean
            get() = listOf(upcomingState, topRatedState, popularState, nowPlayingState).all { it.isLoading }
    }

    data class SectionState<T>(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val data: T? = null
    )
}