package com.velvet.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.velvet.weather.card.CardScreen
import com.velvet.weather.di.getComposeViewModel
import com.velvet.weather.feed.FeedScreen
import com.velvet.weather.theme.AppTheme
import org.koin.core.parameter.parametersOf


@Composable
fun AppScreen() {
    AppTheme {
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(color = Color.Transparent)
        Box(modifier = Modifier.fillMaxSize()) {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.Feed.route) {
                composable(route = Screen.Feed.route) {
                    FeedScreen(viewModel = getComposeViewModel(), onShowCard = { cardName ->
                        navController.navigate(route = Screen.Cards.createRoute(cardName))
                    })
                }
                composable(route = Screen.Cards.route) {
                    CardScreen(
                        viewModel = getComposeViewModel(parameters = {
                            parametersOf(
                                Screen.Cards.getArgumentId(
                                    it
                                )
                            )
                        }),
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
