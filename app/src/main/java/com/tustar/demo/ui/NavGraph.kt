package com.tustar.demo.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.tustar.demo.ui.MainDestinations.DEMO_DETAIL_ID_KEY
import com.tustar.demo.ui.home.DemoDetailDispatcher
import com.tustar.demo.ui.home.DemosScreen
import com.tustar.demo.ui.me.MeScreen
import com.tustar.demo.util.Logger

/**
 * Destinations used in the ([DemoApp]).
 */
object MainDestinations {
    const val DEMO_ROUTE = "demos"
    const val DEMO_DETAIL_ID_KEY = "demoId"
    const val ME_ROUTE = "me"
}

@Composable
fun NavGraph(
    viewModel: MainViewModel = viewModel(),
    updateLocation: () -> Unit = {},
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
            )
        }
        composable(
            "${MainDestinations.DEMO_ROUTE}/{$DEMO_DETAIL_ID_KEY}",
            arguments = listOf(
                navArgument(DEMO_DETAIL_ID_KEY) { type = NavType.IntType }
            ),
            // adb shell am start -d "tustar://demos/2131689633" -a android.intent.action.VIEW
            deepLinks = listOf(navDeepLink {
                uriPattern = "tustar://${MainDestinations.DEMO_ROUTE}/{${DEMO_DETAIL_ID_KEY}}"
            })
        ) {
            val arguments = requireNotNull(it.arguments)
            val currentDemoId = arguments.getInt(DEMO_DETAIL_ID_KEY)
            Logger.d("currentDemoId=$currentDemoId")
            DemoDetailDispatcher(
                demoId = currentDemoId,
                upPress = { actions.upPress() }
            )
        }
        composable(MainDestinations.ME_ROUTE) {
            MeScreen()
        }
    }
}

/**
 * Models the navigation actions in the app.
 */
class MainActions(navController: NavHostController) {
    val openDemo = { id: Int ->
        navController.navigate("${MainDestinations.DEMO_ROUTE}/$id")
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
