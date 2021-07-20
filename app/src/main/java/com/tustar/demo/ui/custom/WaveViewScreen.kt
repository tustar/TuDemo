package com.tustar.demo.ui.custom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.tustar.annotation.DemoItem
import com.tustar.demo.R
import com.tustar.demo.ui.DetailTopBar
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.*
import kotlin.properties.Delegates

const val MAX_AMPLITUDE = 100.0F
const val MIN_OFFSET_Y = 6.0F

@DelicateCoroutinesApi
@DemoItem(
    group = R.string.group_custom_widget,
    item = R.string.custom_wave_view,
    createdAt = "2021-07-01 14:49:50",
    updatedAt = "2021-07-01 14:49:50",
)
@Composable
fun WaveViewScreen(demoItem: Int, upPress: () -> Unit) {
    var apmState = remember { mutableStateOf(30) }

    val context = LocalContext.current
    val startAction = { RecorderService.startRecording(context) }
    val stopAction = { RecorderService.stopRecording(context) }

    Column {
        DetailTopBar(demoItem, upPress)
        WaveView(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(), apmState.value
        )
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            TextButton(onClick = startAction) {
                Text(text = "Start")
            }
            TextButton(onClick = stopAction) {
                Text(text = "Stop")
            }
        }
    }
}

@Composable
private fun WaveView(modifier: Modifier = Modifier, currentAmp: Int) {
    val source = VoiceSource().apply {
        data = currentAmp
    }
    val lineWidth = 6f
    val lineGap = 6f
    val radius = 5f
    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val cx = canvasWidth / 2
        val cy = canvasHeight / 2
        val startX0 = cx - source.queue.size / 2 * (lineWidth + lineGap)
        val scale = currentAmp / MAX_AMPLITUDE
        val path = Path()
        source.queue.forEachIndexed { index, height ->
            val startX = startX0 + index * (lineWidth + lineGap)
            val offsetY = (height * 10 * scale / 2f).coerceAtLeast(MIN_OFFSET_Y)
            path.addRoundRect(
                RoundRect(
                    startX, cy - offsetY, startX + lineWidth, cy + offsetY,
                    radius, radius
                )
            )
        }
        drawPath(
            path = path,
            brush = Brush.horizontalGradient(
                listOf(
                    Color(0xFF6A55FF),
                    Color(0xFF51CCF8),
                    Color(0xFF6A55FF),
                    Color(0xFF48ECF5),
                    Color(0xFF704FFF),
                ),
            )
        )
    }
}

class VoiceSource {
    val queue = LinkedList<Int>()
    private val random = Random()
    var data: Int by Delegates.observable(30) { _, oldValue, newValue ->
        if (oldValue != newValue) {
            queue.clear()
            for (index in 0 until LIMIT) {
                queue.add(random.nextInt(data))
            }
        }
    }

    init {
        for (index in 0 until LIMIT) {
            queue.add(random.nextInt(100))
        }
    }

    companion object {
        private const val LIMIT = 60
    }
}