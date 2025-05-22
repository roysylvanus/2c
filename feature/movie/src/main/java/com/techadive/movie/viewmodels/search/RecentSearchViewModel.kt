package com.techadive.movie.viewmodels.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techadive.common.utils.AppResult
import com.techadive.common.models.KeywordsList
import com.techadive.movie.usecases.search.DeleteAllRecentSearchHistoryUseCase
import com.techadive.movie.usecases.search.GetRecentSearchHistoryUseCase
import com.techadive.movie.usecases.search.SearchKeywordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecentSearchViewModel @Inject constructor(
    private val searchKeywordUseCase: SearchKeywordUseCase,
    private val getRecentSearchHistoryUseCase: GetRecentSearchHistoryUseCase,
    private val deleteAllRecentSearchHistoryUseCase: DeleteAllRecentSearchHistoryUseCase
): ViewModel() {

    private val _recentSearchUIState = MutableStateFlow(RecentSearchUIState())
    val recentSearchUIState: StateFlow<RecentSearchUIState> get() = _recentSearchUIState

    fun onEvent(event: RecentSearchEvent) {
        when(event) {
            is RecentSearchEvent.SearchKeyword -> {
                searchKeyword(event.query)
            }
            is RecentSearchEvent.GetRecentSearchHistory -> {
                getRecentSearchHistory()
            }
            is RecentSearchEvent.DeleteAllRecentSearchHistory -> {
                deleteAllRecentSearchHistory()
            }
        }
    }

    private fun getRecentSearchHistory() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getRecentSearchHistoryUseCase.getRecentSearchHistory().collect { result ->
                    when(result) {
                        is AppResult.Loading -> {
                            _recentSearchUIState.update {
                                it.copy(isLoading = true,
                                    isError = false)
                            }
                        }
                        is AppResult.Error -> {
                            _recentSearchUIState.update {
                                it.copy(
                                    isLoading = false,
                                    isError = true
                                )
                            }
                        }

                        is AppResult.Success -> {
                            _recentSearchUIState.update {
                                it.copy(
                                    keywordsList = result.data,
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

    private fun deleteAllRecentSearchHistory() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                deleteAllRecentSearchHistoryUseCase.deleteAllRecentSearchHistory()
            }
        }
    }

    private fun searchKeyword(query: String?) {
        _recentSearchUIState.update {
            it.copy(
                query = query
            )
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (query != null) {
                    searchKeywordUseCase.searchKeyword(
                        query = query,
                        page = 1
                    ).collect { result ->
                        when (result) {
                            is AppResult.Success -> {
                                _recentSearchUIState.update {
                                    it.copy(
                                        isLoading = false,
                                        isError = false,
                                        keywordsList = result.data
                                    )
                                }

                            }

                            is AppResult.Loading -> {
                                _recentSearchUIState.update {
                                    it.copy(
                                        isLoading = true,
                                        isError = false
                                    )
                                }

                            }

                            is AppResult.Error -> {
                                _recentSearchUIState.update {
                                    it.copy(
                                        isLoading = false,
                                        isError = true
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    sealed class RecentSearchEvent {
        data class SearchKeyword(val query: String?) : RecentSearchEvent()
        data object GetRecentSearchHistory : RecentSearchEvent()
        data object DeleteAllRecentSearchHistory : RecentSearchEvent()
    }

    data class RecentSearchUIState(
        val query: String? = null,
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val keywordsList: KeywordsList? = null
    )
}