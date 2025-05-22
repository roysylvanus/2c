package com.techadive.movie.usecases.favorites

import com.techadive.movie.repositories.favorites.FavoriteRepository
import javax.inject.Inject

interface RemoveFavoriteUseCase {
    suspend fun removeFromFavorites(id: Int)
}

class RemoveFavoriteUseCaseImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository
): RemoveFavoriteUseCase {
    override suspend fun removeFromFavorites(id: Int) {
        favoriteRepository.removeFromFavorites(id)
    }

}