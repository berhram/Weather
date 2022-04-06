package com.velvet.data.network

import com.velvet.data.schemas.geo.CitySchema
import com.velvet.data.schemas.weather.ForecastSchema

interface Network {
    suspend fun findCities(
        appId: String,
        keyword: String
    ) : List<CitySchema>
    suspend fun getWeatherForecast(
        appId: String,
        latitude: String,
        longitude: String,
        exclude: String,
        units: String
    ) : ForecastSchema
}