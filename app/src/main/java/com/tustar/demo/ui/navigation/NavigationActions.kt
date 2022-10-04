package com.tustar.demo.ui.navigation


import android.os.Trace
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.tustar.common.navigation.AppNavigationDestination
import com.tustar.dynamic.navigation.DynamicDestination
import com.tustar.sample.R
import com.tustar.sample.navigation.SamplesDestination
import com.tustar.ui.JankMetricDisposableEffect
import com.tustar.ui.design.icon.DemoIcons
import com.tustar.ui.design.icon.Icon
import com.tustar.weather.navigation.WeatherDestination


data class TopLevelDestination(
    override val route: String,
    override val destination: String,
    val selectedIcon: Icon,
    val unselectedIcon: Icon,
    val iconTextId: Int
) : AppNavigationDestination

class NavigationActions(private val navController: NavHostController) {

    fun navigateTo(destination: TopLevelDestination) {
        Trace.beginSection("Navigation: $destination")
        navController.navigate(destination.route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
        Trace.endSection()
    }
}

/**
 * Top level destinations to be used in the BottomBar and NavRail
 */
val TOP_LEVEL_DESTINATIONS = listOf(
    TopLevelDestination(
        route = SamplesDestination.route,
        destination = SamplesDestination.destination,
        selectedIcon = Icon.DrawableResourceIcon(DemoIcons.Home),
        unselectedIcon = Icon.DrawableResourceIcon(DemoIcons.HomeBorder),
        iconTextId = R.string.sample_feature
    ),
    TopLevelDestination(
        route = WeatherDestination.route,
        destination = WeatherDestination.destination,
        selectedIcon = Icon.DrawableResourceIcon(DemoIcons.Weather),
        unselectedIcon = Icon.DrawableResourceIcon(DemoIcons.WeatherBorder),
        iconTextId = com.tustar.weather.R.string.weather_feature
    ),
    TopLevelDestination(
        route = DynamicDestination.route,
        destination = DynamicDestination.destination,
        selectedIcon = Icon.DrawableResourceIcon(DemoIcons.Dynamic),
        unselectedIcon = Icon.DrawableResourceIcon(DemoIcons.DynamicBorder),
        iconTextId = com.tustar.dynamic.R.string.dynamic_feature
    ),
)

/**
 * Stores information about navigation events to be used with JankStats
 */
@Composable
fun NavigationTrackingSideEffect(navController: NavHostController) {
    JankMetricDisposableEffect(navController) { metricsHolder ->
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            metricsHolder.state?.putState("Navigation", destination.route.toString())
        }

        navController.addOnDestinationChangedListener(listener)

        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }
}

