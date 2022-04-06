package com.velvet.data.schemas.weather

import com.google.gson.annotations.SerializedName

data class TempSchema (
    @SerializedName("min") val min: Double,
    @SerializedName("max") val max: Double,
)