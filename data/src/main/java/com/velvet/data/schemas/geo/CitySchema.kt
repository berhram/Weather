package com.velvet.data.schemas.geo

import com.google.gson.annotations.SerializedName

data class CitySchema(
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lon") val longitude: Double,
)
