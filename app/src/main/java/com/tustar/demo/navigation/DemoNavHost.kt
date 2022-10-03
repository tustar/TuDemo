package com.tustar.demo.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.tustar.common.navigation.AppNavigationDestination
import com.tustar.dynamic.navigation.dynamicGraph
import com.tustar.sample.navigation.samplesGraph
import com.tustar.weather.navigation.WeatherDestination
import com.tustar.weather.navigation.weatherGraph

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DemoNavHost(
    navController: NavHostController,
    onNavigateToDestination: (AppNavigationDestination, String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = WeatherDestination.route
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        samplesGraph(onBackClick)
        weatherGraph()
        dynamicGraph()
    }
}
