package com.tustar.demo.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.navDeepLink
import com.tustar.demo.R
import com.tustar.demo.ui.DetailTopBar
import com.tustar.demo.ui.LocalDemoId
import com.tustar.demo.ui.MainDestinations
import com.tustar.demo.ui.MainViewModel
import com.tustar.demo.ui.custom.ComposesScreens
import com.tustar.demo.ui.custom.PathMeasureScreen
import com.tustar.demo.ui.custom.SvgChinaScreen
import com.tustar.demo.ui.motion.MotionBaseScreen
import com.tustar.demo.ui.motion.MotionImageFilterScreen
import com.tustar.demo.ui.recorder.RecorderScreen
import com.tustar.demo.util.Logger

fun NavGraphBuilder.detail(
    route: String,
    content: @Composable (Int) -> Unit
) {
    composable(
        route = route,
        arguments = listOf(
            navArgument(MainDestinations.DEMO_ID) { type = NavType.IntType }
        ),
        // adb shell am start -d "tustar://demos/compose/2131689632" -a android.intent.action.VIEW
        deepLinks = listOf(navDeepLink {
            uriPattern =
                "tustar://${MainDestinations.DEMO_ROUTE}/{${MainDestinations.DEMO_ID}}"
        })
    ) {
        val arguments = requireNotNull(it.arguments)
        val demoId = arguments.getInt(MainDestinations.DEMO_ID)
        val route = route.replace(
            "{${MainDestinations.DEMO_ID}}",
            demoId.toString()
        )
        Logger.d("route=$route")
        content(demoId)
    }
}

@Composable
fun DemoDetailComposeDispatcher(viewModel: MainViewModel) {
    when (LocalDemoId.current) {
        R.string.custom_wave_view -> RecorderScreen(viewModel)
        R.string.custom_svg_china -> SvgChinaScreen()
        R.string.custom_path_measure -> PathMeasureScreen()
        R.string.sys_motion_base -> MotionBaseScreen()
        R.string.sys_motion_image_filter_view -> MotionImageFilterScreen()
        R.string.custom_composes_example -> ComposesScreens()
        else -> DemoDetailScreen()
    }
}

@Composable
fun DemoDetailScreen() {
    Column {
        DetailTopBar()
        Text(text = "待完善")
    }
}