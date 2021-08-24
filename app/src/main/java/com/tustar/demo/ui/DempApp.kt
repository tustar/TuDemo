package com.tustar.demo.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tustar.demo.R
import com.tustar.demo.ui.theme.DemoTheme
import com.tustar.demo.util.Logger
import java.util.*

@Composable
fun DemoApp(viewModel: MainViewModel) {
    DemoTheme {
        val tabs = remember { MainTabs.values() }
        val navController = rememberNavController()
        val systemUiController = rememberSystemUiController()
        //
        Scaffold(bottomBar = { DemoBottomBar(navController = navController, tabs) }) {
            NavGraph(
                viewModel = viewModel,
                navController = navController,
                systemUiController = systemUiController,
            )
        }
    }
}

@Composable
fun DemoBottomBar(navController: NavController, tabs: Array<MainTabs>) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
        ?: MainTabs.HOME.route
    val routes = remember { MainTabs.values().map { it.route } }
    if (currentRoute in routes) {
        BottomNavigation(Modifier.navigationBarsHeight(additional = 56.dp)) {
            tabs.forEach { tab ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(tab.icon), contentDescription = null,
                            modifier = Modifier.size(28.dp, 28.dp)
                        )
                    },
                    label = { Text(stringResource(tab.title).uppercase(Locale.getDefault())) },
                    selected = currentRoute == tab.route,
                    onClick = {
                        if (tab.route != currentRoute) {
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    alwaysShowLabel = false,
                    modifier = Modifier.navigationBarsPadding()
                )
            }
        }
    }
}

enum class MainTabs(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    HOME(R.string.title_home, R.drawable.ic_tab_home, MainDestinations.DEMO_ROUTE),
    Me(R.string.title_me, R.drawable.ic_tab_me, MainDestinations.ME_ROUTE),
}