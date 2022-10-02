package com.tustar.demo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tustar.common.navigation.AppNavigationDestination
import com.tustar.sample.navigation.SamplesDestination
import com.tustar.sample.navigation.sampleGraph
import com.tustar.weather.ui.weatherGraph

@Composable
fun DemoNavHost(
    navController: NavHostController,
    onNavigateToDestination: (AppNavigationDestination, String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = SamplesDestination.route
) {
    val systemUiController = rememberSystemUiController()
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        sampleGraph(onBackClick)
        weatherGraph()
    }
}
