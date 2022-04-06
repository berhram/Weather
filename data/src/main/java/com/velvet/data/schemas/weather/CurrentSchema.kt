package com.velvet.data.schemas.weather

import com.google.gson.annotations.SerializedName

data class CurrentSchema(
    @SerializedName("dt") val time: Long?,
    @SerializedName("temp") val temp: Double?,
    @SerializedName("feels_like") val feelsLike: Double?,
    @SerializedName("pressure") val pressure: Double?,
    @SerializedName("humidity") val humidity: Double?,
    @SerializedName("clouds") val clouds: Double?,
    @SerializedName("visibility") val visibility: Double?,
    @SerializedName("wind_speed") val windSpeed: Double?,
    @SerializedName("wind_deg") val windDeg: Double?,
)
