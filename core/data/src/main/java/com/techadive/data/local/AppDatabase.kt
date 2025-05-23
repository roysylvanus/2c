package com.techadive.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.techadive.data.local.converters.DatesConverter
import com.techadive.data.local.converters.GenreListConverter
import com.techadive.data.local.converters.MovieListConverter
import com.techadive.data.local.dao.FavoritesDao
import com.techadive.data.local.dao.MovieDao
import com.techadive.data.local.dao.SearchDao
import com.techadive.data.local.entities.FavoriteEntity
import com.techadive.data.local.entities.KeywordEntity
import com.techadive.data.local.entities.MovieListEntity

/**
 * Room database for the Movies2C app.
 *
 * Contains DAOs for movies, search queries, and favorites.
 * Uses custom TypeConverters for handling complex data types like genres and date ranges.
 */
@Database(
    entities = [MovieListEntity::class, KeywordEntity::class, FavoriteEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(GenreListConverter::class, DatesConverter::class, MovieListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun searchDao(): SearchDao
    abstract fun favoritesDao(): FavoritesDao
}
