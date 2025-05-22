package com.techadive.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.techadive.data.local.entities.FavoriteEntity

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites")
    suspend fun getFavorites(): List<FavoriteEntity>

    // Insert a repository into favorites
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(favorite: FavoriteEntity)

    // Remove a repository from favorites
    @Query("DELETE FROM favorites WHERE movieId = :id")
    suspend fun removeFromFavorites(id: Int)

    // Check if a repository is already in favorites
    @Query("SELECT COUNT(*) FROM favorites WHERE movieId = :id")
    suspend fun isFavorite(id: Int): Int

}