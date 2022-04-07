package com.velvet.data

object Settings {
    const val NO_NEW_CALL_TIME_MILLIS = 5 * 60 * 1000
    const val OUTDATED_TIME_MILLIS = 24 * 60 * 60 * 1000
    const val DATE_FORMAT_SHORT = "dd.MM"
    const val DATE_FORMAT_LONG = "dd.MM.yyyy HH:mm"
    const val SHARED_PREFS = "Weather app shared prefs"
    const val LAST_UPDATED_KEY = "Last time updated key"
    const val EXCLUDED = "minutely, alerts, hourly"
    const val API_KEY = "f450d850c69f20b43ce494b45b03a567"
    const val UNITS = "metric"
    const val DB_NAME = "weather-app-db"
    const val WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val GEO_BASE_URL = "https://api.openweathermap.org/geo/1.0/"
}