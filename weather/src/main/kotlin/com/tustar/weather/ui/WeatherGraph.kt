package com.tustar.weather.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.google.accompanist.systemuicontroller.SystemUiController
import com.tustar.weather.ui.WeatherDestinations.ROUTE_WEATHER
import com.tustar.weather.ui.WeatherDestinations.ROUTE_WEATHER_ADD_CITY
import com.tustar.weather.ui.WeatherDestinations.ROUTE_WEATHER_HOME

object WeatherDestinations {
    const val ROUTE_WEATHER = "weather"
    const val ROUTE_WEATHER_HOME = "weather/home"
    const val ROUTE_WEATHER_ADD_CITY = "weather/add_city"
}

fun NavGraphBuilder.weatherGraph(
    navController: NavHostController,
    systemUiController: SystemUiController,
    viewModel: WeatherViewModel,
) {
    navigation(route = ROUTE_WEATHER, startDestination = ROUTE_WEATHER_HOME) {
        composable(ROUTE_WEATHER_HOME) {
            WeatherScreen(navController, systemUiController, viewModel)
        }
        composable(ROUTE_WEATHER_ADD_CITY) {
            AddCityScreen(navController, viewModel)
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

    fun addCity(navController: NavHostController) = { ->
        navController.navigate(ROUTE_WEATHER_ADD_CITY)
    }
}