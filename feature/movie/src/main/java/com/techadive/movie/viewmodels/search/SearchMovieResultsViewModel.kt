package com.techadive.movie.viewmodels.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techadive.common.AppResult
import com.techadive.common.models.MovieList
import com.techadive.movie.usecases.search.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchMovieResultsViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
) : ViewModel() {

    private val _searchMovieResultsUIState = MutableStateFlow(SearchMovieResultsUIState())
    val searchMovieResultsUIState: StateFlow<SearchMovieResultsUIState> get() = _searchMovieResultsUIState

    fun searchMovies(query: String?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (query != null) {
                    searchMoviesUseCase.searchMovies(
                        query = query,
                        includeAdult = false,
                        page = 1,
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
                                Log.i("_searchMovieResultsUIState", result.error.toString())

                                _searchMovieResultsUIState.update {
                                    it.copy(
                                        isLoading = false,
                                        isError = true
                                    )
                                }
                            }

                            is AppResult.Success -> {
                                Log.i("_searchMovieResultsUIState", result.data.toString())
                                _searchMovieResultsUIState.update {
                                    it.copy(
                                        movieList = result.data,
                                        isLoading = false,
                                        isError = false
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