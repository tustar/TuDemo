package com.tustar.weather.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.systemuicontroller.SystemUiController
import com.tustar.weather.ui.WeatherDestinations.ROUTE_WEATHER
import com.tustar.weather.ui.WeatherDestinations.ROUTE_WEATHER_ADD
import com.tustar.weather.ui.WeatherDestinations.ROUTE_WEATHER_MANAGE
import com.tustar.weather.ui.WeatherDestinations.ROUTE_WEATHER_HOME

object WeatherDestinations {
    const val ROUTE_WEATHER = "weather"
    const val ROUTE_WEATHER_HOME = "weather/home"
    const val ROUTE_WEATHER_MANAGE = "weather/manage"
    const val ROUTE_WEATHER_ADD = "weather/add"
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.weatherGraph(
    navController: NavHostController,
    systemUiController: SystemUiController,
    viewModel: WeatherViewModel,
) {
    val onBackPressed = { navController.popBackStack()}
    navigation(route = ROUTE_WEATHER, startDestination = ROUTE_WEATHER_HOME) {
        composable(route = ROUTE_WEATHER_HOME) {
            WeatherScreen(systemUiController, viewModel, WeatherActions.manageCity(navController))
        }

        composable(route = ROUTE_WEATHER_MANAGE) {
            ManageCityScreen(systemUiController, viewModel, onBackPressed,
                WeatherActions.addCity(navController))
        }

        composable(route = ROUTE_WEATHER_ADD) {
            AddCityScreen(viewModel, onBackPressed, WeatherActions.popBackToWeather(navController))
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

    fun popBackToWeather(navController: NavHostController) = { ->
        navController.popBackStack(ROUTE_WEATHER_HOME, false, saveState = true)
    }

    fun manageCity(navController: NavHostController) = { ->
        navController.navigate(ROUTE_WEATHER_MANAGE)
    }

    fun addCity(navController: NavHostController) = { ->
        navController.navigate(ROUTE_WEATHER_ADD)
    }
}