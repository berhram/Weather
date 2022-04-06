package com.velvet.data.network

import com.velvet.data.schemas.weather.ForecastSchema
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET
    suspend fun getWeatherForecast(
        @Query("appid") appId: String,
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("exclude") exclude: String,
        @Query("units") units: String
    ) : ForecastSchema
}