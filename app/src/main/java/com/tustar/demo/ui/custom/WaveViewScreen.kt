package com.tustar.demo.ui.custom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.tustar.annotation.DemoItem
import com.tustar.demo.R
import com.tustar.demo.ui.DetailTopBar
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

val heights = intArrayOf(
    2, 3, 10, 5, 20, 30, 20, 10, 6, 5,
    4, 6, 20, 10, 40, 60, 40, 20, 12, 5,
    2, 3, 10, 5, 20, 30, 20, 10, 6, 5,
    4, 6, 20, 10, 40, 60, 40, 20, 12, 5,
    2, 3, 10, 5, 20, 30, 20, 10, 6, 5,
    4, 6, 20, 10, 40, 60, 40, 20, 12, 5,
    2, 3, 10, 5, 20, 30, 20, 10, 6, 5,
    4, 6, 20, 10, 40, 60, 40, 20, 12, 5,
    2, 5, 10, 6, 2,
)
const val MAX_AMPLITUDE = 100.0F

@DelicateCoroutinesApi
@DemoItem(
    group = R.string.group_custom_widget,
    item = R.string.custom_wave_view,
    createdAt = "2021-07-01 14:49:50",
    updatedAt = "2021-07-01 14:49:50",
)
@Composable
fun WaveViewScreen(demoItem: Int, upPress: () -> Unit) {
    var currentAmp by remember { mutableStateOf(30) }
    LaunchedEffect(rememberScaffoldState()) {
        val tickerChannel = ticker(delayMillis = 100, initialDelayMillis = 0)
        val job = launch {
            while (isActive) {
                tickerChannel.receive()
                currentAmp = (30..50).random()
            }
        }
        delay(1_000L)
        job.cancel()
        tickerChannel.cancel()
    }

    Column {
        DetailTopBar(demoItem, upPress)
        WaveView(currentAmp)
    }
}

@Composable
private fun WaveView(currentAmp: Int) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val cx = canvasWidth / 2
        val cy = canvasHeight / 2
        val lineWidth = 6f
        val lineGap = 6f
        val startX0 = cx - heights.size / 2 * (lineWidth + lineGap)

        val scale = currentAmp / MAX_AMPLITUDE
        heights.forEachIndexed { index, height ->
            val startX = startX0 + index * (lineWidth + lineGap)
            val lineHeight = height * 10 * scale / 2f
            drawLine(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Red, Color.Blue, Color.Yellow),
                ),
                start = Offset(x = startX, y = cy - lineHeight),
                end = Offset(x = startX, y = cy + lineHeight),
                strokeWidth = lineWidth,
            )
        }
    }
}