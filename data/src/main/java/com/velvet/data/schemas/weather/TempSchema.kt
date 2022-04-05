package com.velvet.data.schemas.weather

import com.google.gson.annotations.SerializedName

data class TempSchema (
    @SerializedName("morn") val morning: Double?,
    @SerializedName("day") val day: Double?,
    @SerializedName("eve") val evening: Double?,
    @SerializedName("night") val night: Double?,
    @SerializedName("min") val min: Double?,
    @SerializedName("max") val max: Double?,
)