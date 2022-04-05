package com.velvet.data.schemas.geo

import com.google.gson.annotations.SerializedName

data class CitySchema(
    @SerializedName("name") val name: String?,
    @SerializedName("local_names") val localNames: LocalNamesSchema,
    @SerializedName("lat") val latitude: Double?,
    @SerializedName("lon") val longitude: Double?,
    @SerializedName("country") val county: String?,
    @SerializedName("state") val state: String?
)
