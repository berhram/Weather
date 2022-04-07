package com.velvet.data.repo

import com.velvet.data.entity.City
import com.velvet.data.schemas.geo.CitySchema

interface Repository {
    suspend fun getWeather() : RepositoryResponse<List<City>>
    suspend fun getFilteredWeather(keyword: String) : List<City>
    suspend fun findCandidates(keyword: String) : Result<List<CitySchema>>
    suspend fun addCity(name: String, latitude: Double, longitude: Double) : Boolean
}