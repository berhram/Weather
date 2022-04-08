package com.velvet.data.repo

import com.velvet.data.entity.City
import com.velvet.data.schemas.geo.CitySchema
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getData() : Flow<List<City>>
    suspend fun getErrorChannel() : Channel<RepositoryErrors>
    suspend fun fetchWeather()
    suspend fun findCandidates(keyword: String) : Result<List<CitySchema>>
    suspend fun addCity(name: String, latitude: Double, longitude: Double)
    suspend fun delete(id: String)
}