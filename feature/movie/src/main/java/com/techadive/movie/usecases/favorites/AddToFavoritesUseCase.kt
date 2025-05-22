package com.techadive.movie.usecases.favorites

import com.techadive.common.models.MovieCardData
import com.techadive.movie.repositories.FavoriteRepository
import javax.inject.Inject

interface AddToFavoritesUseCase {
    suspend fun addToFavorites(favorite: MovieCardData)
}

class AddToFavoritesUseCaseImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : AddToFavoritesUseCase {
    override suspend fun addToFavorites(favorite: MovieCardData) {
        favoriteRepository.addToFavorites(favorite)
    }

}