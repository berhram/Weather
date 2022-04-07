package com.velvet.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City(
    @PrimaryKey val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val time: Long,
    val humanTime: String,
    val temp: String,
    val feelsLike: String,
    val pressure: String,
    val humidity: String,
    val clouds: String,
    val visibility: String,
    val windSpeed: String,
    val windDeg: String,
    val dailyWeather: List<DailyWeather>,
)
