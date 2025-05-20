package com.techadive.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.techadive.data.local.converters.GenreListConverter
import com.techadive.data.local.dao.MovieDao
import com.techadive.data.local.entities.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1
)
@TypeConverters(GenreListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
