package com.velvet.data

import com.google.gson.annotations.SerializedName
import com.velvet.data.schemas.weather.WeatherSchema

data class CityCard(
    val name: String,
    val time: Long,
    val temp: Double,
    val feelsLike: Double,
    val pressure: Double,
    val humidity: Double,
    val clouds: Double,
    val visibility: Double,
    val windSpeed: Double,
    val windDeg: Double,
    val weather: List<WeatherSchema>,
)
