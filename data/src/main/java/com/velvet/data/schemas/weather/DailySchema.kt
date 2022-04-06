package com.velvet.data.schemas.weather

import com.google.gson.annotations.SerializedName

data class DailySchema(
    @SerializedName("dt") val time: Long?,
    @SerializedName("temp") val temp: TempSchema?
)
