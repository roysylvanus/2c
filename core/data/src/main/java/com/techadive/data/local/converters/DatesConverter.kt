package com.techadive.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.techadive.common.models.Dates

class DatesConverter {
    @TypeConverter
    fun fromDates(dates: Dates?): String? {
        return dates?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toDates(json: String?): Dates? {
        return json?.let { Gson().fromJson(it, Dates::class.java) }
    }
}