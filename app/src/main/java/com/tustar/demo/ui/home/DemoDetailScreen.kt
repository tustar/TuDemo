package com.tustar.demo.ui.home

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.tustar.demo.R
import com.tustar.demo.ui.DetailTopBar
import com.tustar.demo.ui.custom.PathMeasureScreen
import com.tustar.demo.ui.custom.SvgChinaScreen
import com.tustar.demo.ui.custom.WaveViewScreen
import com.tustar.demo.ui.motion.MotionBaseScreen
import com.tustar.demo.ui.motion.MotionImageFilterScreen

@Composable
fun DemoDetailDispatcher(demoId: Int, upPress: () -> Unit) {
    when (demoId) {
        R.string.custom_wave_view -> WaveViewScreen(demoId, upPress)
        R.string.custom_svg_china -> SvgChinaScreen(demoId, upPress)
        R.string.custom_path_measure -> PathMeasureScreen(demoId, upPress)
        R.string.sys_motion_base -> MotionBaseScreen(demoId, upPress)
        R.string.sys_motion_image_filter_view -> MotionImageFilterScreen(demoId, upPress)
        R.string.optimize_monitor ->
            startDemoDetailActivity(demoId)
        else -> DemoDetailScreen(demoId, upPress)
    }
}

@Composable
private fun startDemoDetailActivity(demoId: Int) {
    val context = LocalContext.current
    context.startActivity(
        Intent(context, DemoDetailActivity::class.java).apply {
            putExtra(INTENT_KEY_DEMO_ID, demoId)
        }
    )
}

@Composable
fun DemoDetailScreen(demoItem: Int, upPress: () -> Unit) {
    Column {
        DetailTopBar(demoItem, upPress)
        Text(text = "待完善")
    }
}