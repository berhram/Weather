package com.velvet.weather.addcity

sealed class AddCityEffect {
    object GoBack : AddCityEffect()
    object Error : AddCityEffect()
}
