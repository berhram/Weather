package com.velvet.data.schemas.geo

import com.google.gson.annotations.SerializedName

data class CitySchema(
    @SerializedName("name") val name: String,
    @SerializedName("country") val country: String,
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lon") val longitude: Double,
)
