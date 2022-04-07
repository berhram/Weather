package com.velvet.weather

sealed class Screen(val route: String) {
    object Feed : Screen(route = "feed")
    object AddCity : Screen(route = "addcity")
}
