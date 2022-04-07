package com.velvet.data.utils

import android.util.Log
import com.velvet.data.Settings.DATE_FORMAT
import com.velvet.data.Settings.NO_NEW_CALL_TIME_MILLIS
import com.velvet.data.Settings.OUTDATED_TIME_MILLIS
import com.velvet.data.entity.City
import com.velvet.data.entity.DailyWeather
import com.velvet.data.schemas.weather.DailySchema
import com.velvet.data.schemas.weather.ForecastSchema
import java.time.Instant.ofEpochSecond
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

fun Long.isRecently() : Boolean {
    return System.currentTimeMillis() - this <= NO_NEW_CALL_TIME_MILLIS
}

fun Long.isOutdated() : Boolean {
    return System.currentTimeMillis() - this <= OUTDATED_TIME_MILLIS
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
        humanTime = forecast.currentWeather.time.toHumanDate(),
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

fun Long.toHumanDate() : String {
    val formatter = DateTimeFormatter.ISO_INSTANT.format(ofEpochSecond(this))
    val localDate = LocalDate.parse(formatter, DateTimeFormatter.ofPattern(DATE_FORMAT))
    return "${localDate.dayOfMonth} ${localDate.month.toString().lowercase()}"
}

fun List<DailySchema>.toDailyWeather() : List<DailyWeather> {
    val output = ArrayList<DailyWeather>()
    for (schema in this) {
        output.add(schema.toDailyWeather())
    }
    return output
}

fun DailySchema.toDailyWeather() : DailyWeather {
    return DailyWeather(
        date = this.time.toHumanDate(),
        tempMin = this.temp.min.toString(),
        tempMax = this.temp.min.toString()
    )
}