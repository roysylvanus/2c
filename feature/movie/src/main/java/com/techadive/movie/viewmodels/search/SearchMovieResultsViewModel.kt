package com.techadive.movie.viewmodels.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techadive.common.utils.AppResult
import com.techadive.common.models.MovieList
import com.techadive.movie.usecases.favorites.GetFavoritesUseCase
import com.techadive.movie.usecases.search.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.collections.distinctBy
import kotlin.collections.orEmpty
import kotlin.collections.plus

@HiltViewModel
class SearchMovieResultsViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    private val _searchMovieResultsUIState = MutableStateFlow(SearchMovieResultsUIState())
    val searchMovieResultsUIState: StateFlow<SearchMovieResultsUIState> get() = _searchMovieResultsUIState

    private var favorites = emptyList<Int>()

    private fun fetchFavorites() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
               favorites = getFavoritesUseCase.getFavorites().map { it.movieId }
            }
        }
    }

    fun searchMovies(query: String?, page: Int = 1) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _searchMovieResultsUIState.update {
                    it.copy(
                        query = null,
                        movieList = null
                    )
                }

                fetchFavorites()

                if (query != null) {
                    searchMoviesUseCase.searchMovies(
                        query = query,
                        includeAdult = false,
                        page = page,
                    ).collect { result ->

                        when (result) {
                            is AppResult.Loading -> {
                                _searchMovieResultsUIState.update {
                                    it.copy(
                                        isLoading = true,
                                        isError = false
                                    )
                                }

                            }

                            is AppResult.Error -> {
                                _searchMovieResultsUIState.update {
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

                                _searchMovieResultsUIState.update { currentState ->
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
                }
            }
        }
    }

    data class SearchMovieResultsUIState(
        val query: String? = null,
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val movieList: MovieList? = null,
    )
}