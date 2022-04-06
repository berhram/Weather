package com.velvet.data.network

import com.velvet.data.schemas.geo.CitySchema
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoApi {
    @GET
    suspend fun findCities(
        @Query("appid") appId: String,
        @Query("q") keyword: String
    ) : List<CitySchema>
}