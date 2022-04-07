package com.velvet.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.velvet.data.entity.DailyWeather
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun fromOptionValuesList(optionValues: List<DailyWeather?>?): String? {
        if (optionValues == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<DailyWeather?>?>() {}.type
        return gson.toJson(optionValues, type)
    }

    @TypeConverter
    fun toOptionValuesList(optionValuesString: String?): List<DailyWeather?>? {
        if (optionValuesString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<DailyWeather?>?>() {}.type
        return gson.fromJson(optionValuesString, type)
    }
}