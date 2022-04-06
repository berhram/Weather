package com.velvet.data.schemas.weather

import com.google.gson.annotations.SerializedName

data class ForecastSchema(
    @SerializedName("current") val currentWeather: CurrentSchema,
    @SerializedName("daily") val dailyWeather: List<DailySchema>,
)
