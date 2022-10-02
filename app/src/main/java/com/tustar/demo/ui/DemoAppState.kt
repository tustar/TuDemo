package com.tustar.demo.ui

import android.os.Trace
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tustar.common.navigation.AppNavigationDestination
import com.tustar.demo.navigation.TopLevelDestination
import com.tustar.dynamic.navigation.DynamicDestination
import com.tustar.sample.navigation.SamplesDestination
import com.tustar.ui.JankMetricDisposableEffect
import com.tustar.ui.design.icon.DemoIcons
import com.tustar.ui.design.icon.Icon.DrawableResourceIcon
import com.tustar.weather.ui.WeatherDestination
import com.tustar.sample.R as sampleR
import com.tustar.weather.R as weatherR

@Composable
fun rememberDemoAppState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController()
): DemoAppState {
    NavigationTrackingSideEffect(navController)
    return remember(navController, windowSizeClass) {
        DemoAppState(navController, windowSizeClass)
    }
}

@Stable
class DemoAppState(
    val navController: NavHostController,
    val windowSizeClass: WindowSizeClass
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact ||
                windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact

    val shouldShowNavRail: Boolean
        get() = !shouldShowBottomBar

    /**
     * Top level destinations to be used in the BottomBar and NavRail
     */
    val topLevelDestinations: List<TopLevelDestination> = listOf(
        TopLevelDestination(
            route = SamplesDestination.route,
            destination = SamplesDestination.destination,
            selectedIcon = DrawableResourceIcon(DemoIcons.Upcoming),
            unselectedIcon = DrawableResourceIcon(DemoIcons.UpcomingBorder),
            iconTextId = sampleR.string.feature_sample
        ),
        TopLevelDestination(
            route = WeatherDestination.route,
            destination = WeatherDestination.destination,
            selectedIcon = DrawableResourceIcon(DemoIcons.Bookmarks),
            unselectedIcon = DrawableResourceIcon(DemoIcons.BookmarksBorder),
            iconTextId = weatherR.string.feature_weather
        ),
        TopLevelDestination(
            route = DynamicDestination.route,
            destination = DynamicDestination.destination,
            selectedIcon = DrawableResourceIcon(DemoIcons.MenuBook),
            unselectedIcon = DrawableResourceIcon(DemoIcons.MenuBookBorder),
            iconTextId = weatherR.string.feature_weather
        ),
    )

    /**
     * UI logic for navigating to a particular destination in the app. The NavigationOptions to
     * navigate with are based on the type of destination, which could be a top level destination or
     * just a regular destination.
     *
     * Top level destinations have only one copy of the destination of the back stack, and save and
     * restore state whenever you navigate to and from it.
     * Regular destinations can have multiple copies in the back stack and state isn't saved nor
     * restored.
     *
     * @param destination: The [AppNavigationDestination] the app needs to navigate to.
     * @param route: Optional route to navigate to in case the destination contains arguments.
     */
    fun navigate(destination: AppNavigationDestination, route: String? = null) {
        Trace.beginSection("Navigation: $destination")
        if (destination is TopLevelDestination) {
            navController.navigate(route ?: destination.route) {
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
        } else {
            navController.navigate(route ?: destination.route)
        }
        Trace.endSection()
    }

    fun onBackClick() {
        navController.popBackStack()
    }
}

/**
 * Stores information about navigation events to be used with JankStats
 */
@Composable
private fun NavigationTrackingSideEffect(navController: NavHostController) {
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
