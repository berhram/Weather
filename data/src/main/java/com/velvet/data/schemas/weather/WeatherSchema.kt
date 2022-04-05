package com.velvet.data.schemas.weather

import com.google.gson.annotations.SerializedName

data class WeatherSchema(
    @SerializedName("id") val id: Double?,
    @SerializedName("main") val main: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("icon") val icon: String?
)
