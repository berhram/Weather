package com.velvet.data.network

import com.velvet.data.schemas.weather.WeatherSchema
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi : Api {
    @GET
    suspend fun getWeather(
        @Query("appid") appId: String,
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("lang") language: String,
        @Query("exclude") exclude: String,
        @Query("units") units: String
    ) : List<WeatherSchema>

    @GET
    suspend fun getForecastWeather(
        @Query("appid") appId: String,
        @Query("q") cityName: String,
        @Query("cnt") count: String,
        @Query("lang") language: String,
        @Query("units") units: String
    ) : List<WeatherSchema>
}