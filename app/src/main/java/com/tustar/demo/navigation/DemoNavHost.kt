package com.tustar.demo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.tustar.common.navigation.AppNavigationDestination
import com.tustar.dynamic.navigation.dynamicGraph
import com.tustar.sample.navigation.SamplesDestination
import com.tustar.sample.navigation.samplesGraph
import com.tustar.weather.ui.weatherGraph

@Composable
fun DemoNavHost(
    navController: NavHostController,
    onNavigateToDestination: (AppNavigationDestination, String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = SamplesDestination.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        samplesGraph(onBackClick)
        weatherGraph()
        dynamicGraph()
    }
}
