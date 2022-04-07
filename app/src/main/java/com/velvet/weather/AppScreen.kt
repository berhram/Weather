package com.velvet.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.velvet.weather.addcity.AddCityScreen
import com.velvet.weather.di.getComposeViewModel
import com.velvet.weather.feed.FeedScreen

@Composable
fun AppScreen() {
    MaterialTheme {
        Box(Modifier.fillMaxSize()) {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.Feed.route) {
                composable(Screen.Feed.route) {
                    FeedScreen(viewModel = getComposeViewModel(), onAddCity = {
                        navController.navigate(Screen.AddCity.route)
                    })
                }
                composable(Screen.AddCity.route) {
                    AddCityScreen(viewModel = getComposeViewModel(), goBack = {
                        navController.popBackStack()
                    })
                }
            }
        }
    }
}

