package com.techadive.movie.usecases.favorites

import com.techadive.movie.repositories.favorites.FavoriteRepository
import javax.inject.Inject

interface CheckIfMovieIsFavoriteUseCase {
    suspend fun isFavorite(id: Int): Int
}

class CheckIfMovieIsFavoriteUseCaseImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : CheckIfMovieIsFavoriteUseCase {
    override suspend fun isFavorite(id: Int): Int {
        return favoriteRepository.isFavorite(id)
    }

}