package com.velvet.data.repo

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
import com.velvet.data.utils.toHumanDate
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

class RepositoryImpl(
    private val network: Network,
    private val dao: CityDao,
    private val timeStore: TimeStore
    ) : Repository, SafeApiCall {

    private val errorChannel: Channel<RepositoryErrors> = Channel(1)

    override suspend fun getData() : Flow<List<City>> {
        return dao.getAllDistinctUntilChanged()
    }

    override suspend fun getErrorChannel() : Channel<RepositoryErrors> {
        return errorChannel
    }

    override suspend fun fetchWeather() {
        if (timeStore.getTime().isRecently()) {
            updateWeather()
        } else {
            errorChannel.send(RepositoryErrors.RECENTLY)
        }
    }

    override suspend fun findCandidates(keyword: String) = safeApiCall {
        network.findCities(appId = API_KEY, keyword = keyword)
    }

    override suspend fun addCity(name: String, latitude: Double, longitude: Double) {
        val result = downloadCityWeather(latitude = latitude, longitude = longitude)
        if (result.isSuccess &&  result.getOrNull() != null) {
            val forecast = result.getOrNull()!!
                dao.insert(
                    City(
                        id = name + latitude + longitude,
                        name = name,
                        latitude = latitude,
                        longitude = longitude,
                        time = timeStore.getTime(),
                        humanTime = forecast.currentWeather.time.toHumanDate(),
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
            } else {
                errorChannel.send(RepositoryErrors.FAILURE_ADD)
        }
    }

    override suspend fun delete(id: String) {
        dao.delete(dao.getById(id))
    }

    private suspend fun updateWeather() {
        dao.getAll().collectLatest {
            val updatedCities = ArrayList<City>()
            for (city in it) {
                val forecastResult =
                    downloadCityWeather(latitude = city.latitude, longitude = city.longitude)
                if (forecastResult.isSuccess) {
                    val forecast = forecastResult.getOrNull()
                    if (forecast != null) {
                        updatedCities.add(city.addForecast(forecast))
                    }
                } else {
                    errorChannel.send(RepositoryErrors.FAILURE_DOWNLOAD)
                }
            }
            dao.deleteAll(it)
            dao.insertAll(updatedCities)
            timeStore.saveTime(System.currentTimeMillis())
        }
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
