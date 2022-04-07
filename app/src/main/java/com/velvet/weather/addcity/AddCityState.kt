package com.velvet.weather.addcity

import com.velvet.data.schemas.geo.CitySchema

data class AddCityState(val searchText: String = "", val candidates: List<CitySchema> = emptyList())
