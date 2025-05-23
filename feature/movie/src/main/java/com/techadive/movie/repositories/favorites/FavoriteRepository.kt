package com.techadive.movie.repositories.favorites

import com.techadive.common.models.MovieCardData
import com.techadive.data.local.dao.FavoritesDao
import com.techadive.data.local.entities.FavoriteEntity
import com.techadive.data.local.entities.convertToFavorite
import javax.inject.Inject
/**
 * Implementation of [FavoriteRepository] that uses [FavoritesDao] to persist
 * and retrieve favorite movies from the local database.
 */
interface FavoriteRepository {
    suspend fun getFavorites(): List<MovieCardData>
    suspend fun addToFavorites(favorite: MovieCardData)
    suspend fun removeFromFavorites(id: Int)
    suspend fun isFavorite(id: Int): Int
}

class FavoriteRepositoryImpl @Inject constructor(
    private val favoritesDao: FavoritesDao
) : FavoriteRepository {
    override suspend fun getFavorites(): List<MovieCardData> {
        return favoritesDao.getFavorites().map {
            it.convertToFavorite()
        }
    }

    override suspend fun addToFavorites(favorite: MovieCardData) {
        favoritesDao.addToFavorites(
            FavoriteEntity(
                movieId = favorite.movieId,
                releaseDate = favorite.releaseDate,
                originalTitle = favorite.originalTitle,
                voteAverage = favorite.voteAverage,
                posterPath = favorite.posterPath,
                overview = favorite.overview
            )
        )
    }

    override suspend fun removeFromFavorites(id: Int) {
        favoritesDao.removeFromFavorites(id)
    }

    override suspend fun isFavorite(id: Int): Int {
        return favoritesDao.isFavorite(id)
    }

}