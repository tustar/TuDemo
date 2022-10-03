package com.tustar.weather.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.tustar.common.navigation.AppNavigationDestination
import com.tustar.weather.navigation.WeatherDestinations.ROUTE_WEATHER
import com.tustar.weather.navigation.WeatherDestinations.ROUTE_WEATHER_ADD
import com.tustar.weather.navigation.WeatherDestinations.ROUTE_WEATHER_HOME
import com.tustar.weather.navigation.WeatherDestinations.ROUTE_WEATHER_MANAGE
import com.tustar.weather.ui.WeatherScreen
import com.tustar.weather.ui.WeatherViewModel

object WeatherDestinations {
    const val ROUTE_WEATHER = "weather"
    const val ROUTE_WEATHER_HOME = "weather/home"
    const val ROUTE_WEATHER_MANAGE = "weather/manage"
    const val ROUTE_WEATHER_ADD = "weather/add"
}

object WeatherDestination : AppNavigationDestination {
    override val route = "weather_route"
    override val destination = "weather_destination"
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.weatherGraph(
) {
    composable(route = WeatherDestination.route) {
        WeatherRoute()
    }
}

@Composable
fun WeatherRoute(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = hiltViewModel()
) {

    WeatherScreen(
        viewModel = viewModel
    )
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