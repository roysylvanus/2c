package com.techadive.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.techadive.data.local.entities.MovieListEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_lists WHERE category = :category LIMIT 1")
    suspend fun getMovieListByCategory(category: String): MovieListEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieList(movieList: MovieListEntity)

}