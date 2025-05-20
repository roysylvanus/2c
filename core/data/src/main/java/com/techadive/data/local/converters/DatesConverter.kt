package com.techadive.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.techadive.common.models.Dates

class DatesConverter {
    @TypeConverter
    fun fromDates(dates: Dates): String = Gson().toJson(dates)

    @TypeConverter
    fun toDates(json: String): Dates = Gson().fromJson(json, Dates::class.java)
}