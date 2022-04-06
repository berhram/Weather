package com.velvet.data.repo

import com.velvet.data.Settings.API_KEY
import com.velvet.data.Settings.EXCLUDED
import com.velvet.data.Settings.UNITS
import com.velvet.data.entity.City
import com.velvet.data.network.Network
import com.velvet.data.local.CityDao
import com.velvet.data.utils.addForecast

class RepositoryImpl(
    private val network: Network,
    private val dao: CityDao
    ) : Repository, SafeApiCall {

    companion object {

    }

    suspend fun updateWeather() : Result<Unit> {
        val cities = dao.getAll()
        val updatedCities = ArrayList<City>()
        for (city in cities) {
            val forecastResult = downloadCityWeather(city)
            if (forecastResult.isSuccess) {
                val forecast = forecastResult.getOrNull()
                if (forecast != null) {
                    updatedCities.add(city.addForecast(forecast))
                }
            }
            else {
                return Result.failure(Exception("Cannot download data from remote source!"))
            }
        }
        dao.insertAll(updatedCities)
        return Result.success(Unit)
    }

    suspend fun getWeather() : List<City> {
        val cities = dao.getAll()
        val updatedCities = ArrayList<City>()
        for (city in cities) {
            val forecastResult = downloadCityWeather(city)
            if (forecastResult.isSuccess) {
                val forecast = forecastResult.getOrNull()
                if (forecast != null) {
                    updatedCities.add(city.addForecast(forecast))
                }
            }
            else {
                return cities
            }
        }
        dao.insertAll(updatedCities)
        return dao.getAll()
    }

    private suspend fun downloadCityWeather(city: City) = safeApiCall {
        network.getWeatherForecast(appId = API_KEY, latitude = city.latitude.toString(), longitude = city.longitude.toString(), exclude = EXCLUDED, units = UNITS)
    }


}
