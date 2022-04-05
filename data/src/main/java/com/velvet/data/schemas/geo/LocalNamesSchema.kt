package com.velvet.data.schemas.geo

import com.google.gson.annotations.SerializedName
import com.velvet.data.DataConfig

data class LocalNamesSchema(
    @SerializedName(DataConfig.Locales.RUSSIAN) val ru: String?,
    @SerializedName(DataConfig.Locales.ENGLISH) val en: String?
)
