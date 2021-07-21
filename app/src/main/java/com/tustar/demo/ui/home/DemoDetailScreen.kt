package com.tustar.demo.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.navDeepLink
import com.tustar.demo.R
import com.tustar.demo.ui.DetailTopBar
import com.tustar.demo.ui.MainDestinations
import com.tustar.demo.ui.MainViewModel
import com.tustar.demo.ui.custom.PathMeasureScreen
import com.tustar.demo.ui.custom.SvgChinaScreen
import com.tustar.demo.ui.recorder.RecorderScreen
import com.tustar.demo.ui.motion.MotionBaseScreen
import com.tustar.demo.ui.motion.MotionImageFilterScreen
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
fun DemoDetailComposeDispatcher(
    viewModel: MainViewModel, demoId: Int,
    upPress: () -> Unit,
) {
    when (demoId) {
        R.string.custom_wave_view -> RecorderScreen(viewModel, demoId, upPress)
        R.string.custom_svg_china -> SvgChinaScreen(demoId, upPress)
        R.string.custom_path_measure -> PathMeasureScreen(demoId, upPress)
        R.string.sys_motion_base -> MotionBaseScreen(demoId, upPress)
        R.string.sys_motion_image_filter_view -> MotionImageFilterScreen(demoId, upPress)
        else -> DemoDetailScreen(demoId, upPress)
    }
}

@Composable
fun DemoDetailScreen(demoItem: Int, upPress: () -> Unit) {
    Column {
        DetailTopBar(demoItem, upPress)
        Text(text = "待完善")
    }
}