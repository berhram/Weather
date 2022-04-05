package com.velvet.data.schemas.weather

import com.google.gson.annotations.SerializedName

data class DailySchema(
    @SerializedName("dt") val time: Long?,
    @SerializedName("temp") val temp: TempSchema?,
    @SerializedName("feels_like") val feelsLike: FeelsLikeSchema?,
    @SerializedName("pressure") val pressure: Double?,
    @SerializedName("humidity") val humidity: Double?,
    @SerializedName("dew_point") val dewPoint: Double?,
    @SerializedName("wind_speed") val windSpeed: Double?,
    @SerializedName("wind_gust") val windGust: Double?,
    @SerializedName("wind_deg") val windDeg: Double?,
    @SerializedName("clouds") val clouds: Double?,
    @SerializedName("pop") val pop: Double?,
    @SerializedName("rain") val rain: Double?,
    @SerializedName("snow") val snow: Double?,
    @SerializedName("weather") val weather: WeatherSchema?
)
