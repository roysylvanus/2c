package com.techadive.movie.viewmodels.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techadive.common.models.MovieCardData
import com.techadive.movie.usecases.favorites.GetFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    private val _favoritesUIState = MutableStateFlow(FavoritesUIState())
    val favoritesUIState: StateFlow<FavoritesUIState> get() = _favoritesUIState

    fun fetchFavorites() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val favorites = getFavoritesUseCase.getFavorites()

                _favoritesUIState.update {
                    it.copy(
                        favorites = favorites.map {
                            it.copy(isFavorite = true)
                        }
                    )
                }
            }
        }
    }

    data class FavoritesUIState(
        val favorites: List<MovieCardData> = emptyList(),
    )

}