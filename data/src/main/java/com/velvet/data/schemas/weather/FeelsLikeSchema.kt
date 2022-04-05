package com.velvet.data.schemas.weather

import com.google.gson.annotations.SerializedName

data class FeelsLikeSchema(
    @SerializedName("morn") val morning: Double?,
    @SerializedName("day") val day: Double?,
    @SerializedName("eve") val evening: Double?,
    @SerializedName("night") val night: Double?,
)
