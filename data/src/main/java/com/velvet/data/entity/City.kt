package com.velvet.data.entity

data class City(
    val name: String,
    val latitude: String,
    val longitude: String,
    val time: String,
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
