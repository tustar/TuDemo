package com.tustar.demo.ui.recorder

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import java.util.*
import kotlin.math.log10
import kotlin.properties.Delegates

@Composable
fun WaveView(modifier: Modifier = Modifier, decibel: Int) {
    val source = VoiceSource().apply {
        data = decibel
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
        val path = Path()
        source.queue.forEachIndexed { index, maxAmplitude ->
            val startX = startX0 + index * (lineWidth + lineGap)
            val offsetY = (cy * maxAmplitude / 32678 / 2F).coerceAtLeast(MIN_OFFSET_Y)
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
            queue.add(RecorderService.MAX_AMPLITUDE_DEFAULT)
        }
    }

    companion object {
        private const val LIMIT = 60
    }
}