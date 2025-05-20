package com.techadive.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.techadive.data.local.converters.DatesConverter
import com.techadive.data.local.converters.GenreListConverter
import com.techadive.data.local.converters.MovieListConverter
import com.techadive.data.local.dao.MovieDao
import com.techadive.data.local.entities.MovieListEntity

@Database(
    entities = [MovieListEntity::class],
    version = 1
)
@TypeConverters(GenreListConverter::class, DatesConverter::class, MovieListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
