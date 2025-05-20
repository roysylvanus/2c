package com.techadive.data.local.converters

import androidx.room.TypeConverter

class GenreListConverter {
    @TypeConverter
    fun fromGenreIds(genreIds: List<Int>): String = genreIds.joinToString(",")

    @TypeConverter
    fun toGenreIds(data: String): List<Int> =
        if (data.isEmpty()) emptyList() else data.split(",").map { it.toInt() }
}