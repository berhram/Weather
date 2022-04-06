package com.velvet.data.utils

import com.velvet.data.Settings.DATE_FORMAT_LONG
import com.velvet.data.Settings.DATE_FORMAT_SHORT
import com.velvet.data.Settings.NO_NEW_CALL_TIME_MILLIS
import com.velvet.data.entity.City
import com.velvet.data.entity.DailyWeather
import com.velvet.data.schemas.geo.CitySchema
import com.velvet.data.schemas.weather.DailySchema
import com.velvet.data.schemas.weather.ForecastSchema
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

fun Long.isRecently() : Boolean {
    return System.currentTimeMillis() - this <= NO_NEW_CALL_TIME_MILLIS
}

fun City.addForecast(forecast: ForecastSchema) : City {
    val dailyForecast = ArrayList<DailyWeather>()
    if (!forecast.dailyWeather.isNullOrEmpty()) {
        for (day in forecast.dailyWeather) {
            dailyForecast.add(day.toDailyWeather())
        }
    }
    return this.copy(
        time = forecast.currentWeather.time,
        humanTime = forecast.currentWeather.time.toLongHumanDate(),
        temp = forecast.currentWeather.temp.toString(),
        feelsLike = forecast.currentWeather.feelsLike.toString(),
        pressure = forecast.currentWeather.pressure.toString(),
        humidity = forecast.currentWeather.humidity.toString(),
        clouds = forecast.currentWeather.clouds.toString(),
        visibility = forecast.currentWeather.visibility.toString(),
        windSpeed = forecast.currentWeather.windSpeed.toString(),
        windDeg = forecast.currentWeather.windDeg.toString(),
        dailyWeather = dailyForecast
    )
}

fun City.addCoordinates(citySchema: CitySchema) : City {
    return this.copy(
        latitude = citySchema.latitude,
        longitude = citySchema.longitude
    )
}

fun Long.toShortHumanDate() : String {
    return SimpleDateFormat(DATE_FORMAT_SHORT).format(Date(this))
}

fun Long.toLongHumanDate() : String {
    return SimpleDateFormat(DATE_FORMAT_LONG).format(Date(this))
}

fun DailySchema.toDailyWeather() : DailyWeather {
    return DailyWeather(
        date = this.time.toShortHumanDate(),
        tempMin = this.temp.min.toString(),
        tempMax = this.temp.min.toString()
    )
}