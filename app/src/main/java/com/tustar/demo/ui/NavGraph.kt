package com.tustar.demo.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.tustar.demo.ui.MainDestinations.DEMO_DETAIL_ID_KEY
import com.tustar.demo.ui.home.DemoDetailDispatcher
import com.tustar.demo.ui.home.DemosScreen
import com.tustar.demo.ui.me.MeScreen
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Destinations used in the ([DemoApp]).
 */
object MainDestinations {
    const val DEMO_ROUTE = "demos"
    const val DEMO_DETAIL_ROUTE = "demo"
    const val DEMO_DETAIL_ID_KEY = "demoId"
    const val ME_ROUTE = "me"
}

@Composable
fun NavGraph(
    viewModel: MainViewModel = viewModel(),
    modifier: Modifier = Modifier,
    updateLocation: () -> Unit = {},
    finishActivity: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainDestinations.DEMO_ROUTE,
) {
    val actions = remember(navController) { MainActions(navController) }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainDestinations.DEMO_ROUTE) {
            DemosScreen(
                viewModel = viewModel,
                updateLocation = updateLocation,
                onDemoClick = actions.openDemo,
                modifier = modifier,
            )
        }
        composable(
            "${MainDestinations.DEMO_DETAIL_ROUTE}/{$DEMO_DETAIL_ID_KEY}",
            arguments = listOf(
                navArgument(DEMO_DETAIL_ID_KEY) { type = NavType.IntType }
            )
        ) { backStackEntry: NavBackStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val currentDemoId = arguments.getInt(DEMO_DETAIL_ID_KEY)
            DemoDetailDispatcher(
                demoId = currentDemoId,
                upPress = { actions.upPress() }
            )
        }
        composable(MainDestinations.ME_ROUTE) {
            MeScreen(modifier = modifier)
        }
    }
}

/**
 * Models the navigation actions in the app.
 */
class MainActions(navController: NavHostController) {
    val openDemo = { id: Int ->
        navController.navigate("${MainDestinations.DEMO_DETAIL_ROUTE}/$id")
    }

    val upPress = { navController.navigateUp() }
}


/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED
