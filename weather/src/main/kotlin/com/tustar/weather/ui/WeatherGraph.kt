package com.tustar.weather.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.google.accompanist.systemuicontroller.SystemUiController
import com.tustar.weather.ui.WeatherDestinations.ROUTE_WEATHER
import com.tustar.weather.ui.WeatherDestinations.ROUTE_WEATHER_HOME

object WeatherDestinations {
    const val ROUTE_WEATHER = "weather"
    const val ROUTE_WEATHER_HOME = "weather/home"
}

fun NavGraphBuilder.weatherGraph(
    navController: NavHostController,
    systemUiController: SystemUiController,
    viewModel: WeatherViewModel,
) {
    navigation(route = ROUTE_WEATHER, startDestination = ROUTE_WEATHER_HOME) {
        composable(ROUTE_WEATHER_HOME) {
            WeatherScreen(systemUiController, viewModel)
        }
    }
}

/**
 * Models the navigation actions in the app.
 */
object WeatherActions {
    fun openWeather(navController: NavHostController) = { ->
        navController.navigate(ROUTE_WEATHER)
    }
}