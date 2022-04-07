package com.velvet.data

object Settings {
    //TEST 10 * 1000 PROD 5 * 60 * 1000
    const val NO_NEW_CALL_TIME_MILLIS = 10 * 1000
    //TEST 30 * 1000 PROD 24 * 60 * 60 * 1000
    const val OUTDATED_TIME_MILLIS = 30 * 1000
    const val DATE_FORMAT_SHORT = "dd.MM"
    const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    const val SHARED_PREFS = "Weather app shared prefs"
    const val LAST_UPDATED_KEY = "Last time updated key"
    const val EXCLUDED = "minutely, alerts, hourly"
    const val API_KEY = "f450d850c69f20b43ce494b45b03a567"
    const val UNITS = "metric"
    const val DB_NAME = "weather-app-db"
    const val WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val GEO_BASE_URL = "https://api.openweathermap.org/geo/1.0/"
    const val DEFAULT_CITY_1_NAME = "Moscow"
    const val DEFAULT_CITY_2_NAME = "Saint Petersburg"
    const val DEFAULT_CITY_1_LATITUDE = 55.7522
    const val DEFAULT_CITY_2_LATITUDE = 59.8944
    const val DEFAULT_CITY_1_LONGITUDE = 37.6156
    const val DEFAULT_CITY_2_LONGITUDE = 30.2642
    const val ANIMATION_DURATION = 500
}