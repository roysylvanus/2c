package com.techadive.movie.usecases.favorites

import com.techadive.common.models.MovieCardData
import com.techadive.movie.repositories.FavoriteRepository
import javax.inject.Inject

interface GetFavoritesUseCase {
    suspend fun getFavorites(): List<MovieCardData>
}

class GetFavoritesUseCaseImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository
): GetFavoritesUseCase {
    override suspend fun getFavorites(): List<MovieCardData> {
        return favoriteRepository.getFavorites()
    }
}