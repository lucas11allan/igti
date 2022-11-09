package com.projectigti.irontime.extension

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.Date

class Converters {

    @TypeConverter
    fun listDateToJson(value: List<Date>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonDateToList(value: String) = Gson().fromJson(value, Array<Date>::class.java).toList()
}