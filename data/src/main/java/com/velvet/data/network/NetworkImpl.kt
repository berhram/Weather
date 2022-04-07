package com.velvet.data.network

import android.util.Log
import com.velvet.data.BuildConfig
import com.velvet.data.Settings.GEO_BASE_URL
import com.velvet.data.Settings.WEATHER_BASE_URL
import com.velvet.data.schemas.geo.CitySchema
import com.velvet.data.schemas.weather.ForecastSchema
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.coroutineContext

class NetworkImpl : Network {
    private var geoService: GeoApi
    private var weatherService: WeatherApi

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BASIC
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        geoService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(GEO_BASE_URL)
            .client(httpClient)
            .build()
            .create(GeoApi::class.java)
        weatherService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(WEATHER_BASE_URL)
            .client(httpClient)
            .build()
            .create(WeatherApi::class.java)
    }

    override suspend fun findCities(appId: String, keyword: String) : List<CitySchema> {
        Log.d("COR", "find cities invoked $coroutineContext")

        return geoService.findCities(appId = appId, keyword = keyword)
    }

    override suspend fun getWeatherForecast(
        appId: String,
        latitude: String,
        longitude: String,
        exclude: String,
        units: String
    ) : ForecastSchema {
        Log.d("COR", "get weather forecast $coroutineContext")

        return weatherService.getWeatherForecast(
            appId = appId,
            latitude = latitude,
            longitude = longitude,
            exclude = exclude,
            units = units
        )
    }
}