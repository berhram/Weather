package com.velvet.data.repo

import com.velvet.data.Settings
import com.velvet.data.Settings.API_KEY
import com.velvet.data.Settings.EXCLUDED
import com.velvet.data.Settings.UNITS
import com.velvet.data.entity.City
import com.velvet.data.network.Network
import com.velvet.data.local.CityDao
import com.velvet.data.local.TimeStore
import com.velvet.data.utils.addForecast
import com.velvet.data.utils.isRecently
import com.velvet.data.utils.toDailyWeather
import com.velvet.data.utils.toLongHumanDate

class RepositoryImpl(
    private val network: Network,
    private val dao: CityDao,
    private val timeStore: TimeStore
    ) : Repository, SafeApiCall {

    override suspend fun getWeather() : RepositoryResponse<List<City>> {
        if (timeStore.getTime().isRecently()) {
            return RepositoryResponse.Recently(dao.getAll())
        }
        return if (updateWeather()) {
            RepositoryResponse.Success(dao.getAll())
        } else {
            RepositoryResponse.ErrorFailure
        }
    }

    override suspend fun getFilteredWeather(keyword: String) : List<City> {
        return dao.getAll().filter { it.name.contains(other = keyword, ignoreCase = true) }
    }

    override suspend fun findCandidates(keyword: String) = safeApiCall {
        network.findCities(appId = API_KEY, keyword = keyword)
    }

    override suspend fun addCity(name: String, latitude: Double, longitude: Double): Boolean {
        val result = downloadCityWeather(latitude = latitude, longitude = longitude)
        if (result.isSuccess) {
            val forecast = result.getOrNull()
            if (forecast != null) {
                dao.insert(
                    City(
                        id = name + latitude + longitude,
                        name = name,
                        latitude = latitude,
                        longitude = longitude,
                        time = System.currentTimeMillis(),
                        humanTime = forecast.currentWeather.time.toLongHumanDate(),
                        temp = forecast.currentWeather.temp.toString(),
                        feelsLike = forecast.currentWeather.feelsLike.toString(),
                        pressure = forecast.currentWeather.pressure.toString(),
                        humidity = forecast.currentWeather.humidity.toString(),
                        clouds = forecast.currentWeather.clouds.toString(),
                        visibility = forecast.currentWeather.visibility.toString(),
                        windSpeed = forecast.currentWeather.windSpeed.toString(),
                        windDeg = forecast.currentWeather.windDeg.toString(),
                        dailyWeather = forecast.dailyWeather.toDailyWeather()
                    )
                )
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }

    override suspend fun delete(id: String) {
        dao.delete(dao.getById(id)[0])
    }

    private suspend fun updateWeather() : Boolean {
        val cities = dao.getAll()
        val updatedCities = ArrayList<City>()
        for (city in cities) {
            val forecastResult = downloadCityWeather(latitude = city.latitude, longitude = city.longitude)
            if (forecastResult.isSuccess) {
                val forecast = forecastResult.getOrNull()
                if (forecast != null) {
                    updatedCities.add(city.addForecast(forecast))
                } else { return false }
            } else { return false }
        }
        dao.deleteAll(cities)
        dao.insertAll(updatedCities)
        timeStore.saveTime(System.currentTimeMillis())
        return true
    }

    private suspend fun downloadCityWeather(latitude: Double, longitude: Double) = safeApiCall {
        network.getWeatherForecast(
            appId = API_KEY,
            latitude = latitude.toString(),
            longitude = longitude.toString(),
            exclude = EXCLUDED,
            units = UNITS
        )
    }
}
