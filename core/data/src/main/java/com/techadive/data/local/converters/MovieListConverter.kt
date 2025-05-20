package com.techadive.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.techadive.common.models.Movie

class MovieListConverter {
    @TypeConverter
    fun fromMovieList(movies: List<Movie>): String = Gson().toJson(movies)

    @TypeConverter
    fun toMovieList(json: String): List<Movie> {
        val type = object : TypeToken<List<Movie>>() {}.type
        return Gson().fromJson(json, type)
    }
}