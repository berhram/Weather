package com.velvet.data.schemas.weather

import com.google.gson.annotations.SerializedName

data class ForecastSchema(
    @SerializedName("lat") val latitude: Double?,
    @SerializedName("lon") val longitude: Double?,
    @SerializedName("timezone_offset") val timezoneOffset: Long?,
    @SerializedName("current") val currentWeather: CurrentSchema?,
    @SerializedName("daily") val dailyWeather: List<DailySchema>?,
)
