package com.techadive.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.techadive.data.local.entities.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(entity: List<MovieEntity>)

    @Query("SELECT * FROM movies")
    suspend fun getMovies(): List<MovieEntity>

}