package com.tustar.demo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.tustar.demo.R
import com.tustar.demo.data.Weather
import com.tustar.demo.ktx.putParcelable
import com.tustar.demo.ui.MainDestinations.DEMO_DETAIL_ANDROID_ROUTE
import com.tustar.demo.ui.MainDestinations.DEMO_DETAIL_COMPOSE_ROUTE
import com.tustar.demo.ui.MainDestinations.DEMO_ID
import com.tustar.demo.ui.MainDestinations.DEMO_ROUTE
import com.tustar.demo.ui.MainDestinations.KEY_WEATHER
import com.tustar.demo.ui.MainDestinations.ME_ROUTE
import com.tustar.demo.ui.MainDestinations.WEATHER_ROUTE
import com.tustar.demo.ui.home.*
import com.tustar.demo.ui.me.MeScreen
import com.tustar.demo.ui.weather.WeatherScreen

/**
 * Destinations used in the ([DemoApp]).
 */
object MainDestinations {
    const val DEMO_ROUTE = "demos"
    const val DEMO_DETAIL_COMPOSE_ROUTE = "demos/compose"
    const val DEMO_DETAIL_ANDROID_ROUTE = "demos/android"
    const val WEATHER_ROUTE = "weather"
    const val ME_ROUTE = "me"

    //
    const val DEMO_ID = "demoId"
    const val KEY_WEATHER = "weather"
}

@Composable
fun NavGraph(
    viewModel: MainViewModel = MainViewModel(),
    updateLocation: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
    startDestination: String = DEMO_ROUTE,
) {
    val actions = remember(navController) { MainActions(navController) }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(DEMO_ROUTE) {
            DemosScreen(
                viewModel = viewModel,
                updateLocation = updateLocation,
                onDemoClick = actions.openDemo,
                onWeatherClick = actions.openWeather
            )
        }
        detail(route = "${DEMO_DETAIL_COMPOSE_ROUTE}/{$DEMO_ID}") {
            CompositionLocalProvider(
                LocalBackPressedDispatcher provides { navController.navigateUp() },
                LocalDemoId provides it
            ) {
                DemoDetailComposeDispatcher(viewModel)
            }

        }
        detail(route = "${DEMO_DETAIL_ANDROID_ROUTE}/{$DEMO_ID}") {
            actions.startDemoDetailActivity(LocalContext.current, it)
        }
        composable(WEATHER_ROUTE) { entry ->
            val weather = entry.arguments?.getParcelable<Weather>(KEY_WEATHER)
            weather?.let {
                WeatherScreen(it)
            }
        }
        composable(ME_ROUTE) {
            MeScreen()
        }
    }
}

/**
 * Models the navigation actions in the app.
 */
class MainActions(navController: NavHostController) {
    val openDemo = { demoId: Int ->
        val host = when (demoId) {
            R.string.optimize_monitor -> DEMO_DETAIL_ANDROID_ROUTE
            else -> DEMO_DETAIL_COMPOSE_ROUTE
        }
        navController.navigate("$host/${demoId}")
    }

    val startDemoDetailActivity = { context: Context, demoId: Int ->
        context.startActivity(
            Intent(context, DemoDetailActivity::class.java).apply {
                putExtra(INTENT_KEY_DEMO_ID, demoId)
            }
        )
    }

    val openWeather = { weather: Weather ->
        navController.putParcelable(KEY_WEATHER, weather)
        navController.navigate(WEATHER_ROUTE)
    }
}


/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED


