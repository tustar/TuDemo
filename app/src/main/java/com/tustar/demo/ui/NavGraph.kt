package com.tustar.demo.ui

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tustar.demo.R
import com.tustar.demo.ui.MainDestinations.DEMO_ID
import com.tustar.demo.ui.MainDestinations.ROUTE_DEMOS
import com.tustar.demo.ui.MainDestinations.ROUTE_DEMOS_ANDROID
import com.tustar.demo.ui.MainDestinations.ROUTE_DEMOS_COMPOSE
import com.tustar.demo.ui.MainDestinations.ROUTE_ME
import com.tustar.demo.ui.home.*
import com.tustar.demo.ui.me.MeScreen
import com.tustar.weather.ui.WeatherActions
import com.tustar.weather.ui.weatherGraph

/**
 * Destinations used in the ([DemoApp]).
 */
object MainDestinations {
    const val ROUTE_DEMOS = "demos"
    const val ROUTE_DEMOS_COMPOSE = "demos/compose"
    const val ROUTE_DEMOS_ANDROID = "demos/android"
    const val ROUTE_ME = "me"

    //
    const val DEMO_ID = "demoId"
}

@Composable
fun NavGraph(
    viewModel: MainViewModel,
    navController: NavHostController = rememberNavController(),
    systemUiController: SystemUiController = rememberSystemUiController(),
    startDestination: String = ROUTE_DEMOS,
) {
    val actions = remember(navController) { MainActions(navController) }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ROUTE_DEMOS) {
            DemosScreen(
                systemUiController = systemUiController,
                viewModel = viewModel,
                onDemoClick = actions.openDemo,
                onWeatherClick = actions.openWeather
            )
        }
        detail(route = "${ROUTE_DEMOS_COMPOSE}/{$DEMO_ID}") {
            CompositionLocalProvider(
                LocalBackPressedDispatcher provides { actions.upPress },
                LocalDemoId provides it
            ) {
                DemoDetailComposeDispatcher(viewModel)
            }

        }
        detail(route = "${ROUTE_DEMOS_ANDROID}/{$DEMO_ID}") {
            actions.startDemoDetailActivity(LocalContext.current, it)
        }
        composable(route = ROUTE_ME) {
            MeScreen(systemUiController = systemUiController)
        }
        weatherGraph(navController, systemUiController, viewModel)
    }
}

/**
 * Models the navigation actions in the app.
 */
class MainActions(navController: NavHostController) {
    val openDemo = { demoId: Int ->
        val host = when (demoId) {
            R.string.optimize_monitor -> ROUTE_DEMOS_ANDROID
            else -> ROUTE_DEMOS_COMPOSE
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

    val openWeather = WeatherActions.openWeather(navController)

    val upPress = navController.navigateUp()
}


/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED



