package com.tustar.weather.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tustar.data.source.remote.Hourly
import com.tustar.weather.R
import com.tustar.weather.util.TrendSwitchMode


@Composable
fun ItemWeather24H(hourly24: List<Hourly>, mode24H: TrendSwitchMode) {
    ItemWeatherSwitch(
        titleId = R.string.weather_15d_forecast,
        trendMode = mode24H,
        list = { modifier ->
            ItemWeather24HList(modifier, hourly24 = hourly24)
        },
        trend = { modifier ->
            ItemWeather24HTrend(modifier, hourly24 = hourly24)
        },
    )
}

@Composable
private fun ItemWeather24HList(modifier: Modifier, hourly24: List<Hourly>) {
    LazyRow(
        modifier = modifier,
    ) {
        items(items = hourly24, itemContent = { hourly ->
            ItemWeather24ListColumn(hourly = hourly)
        })
    }
}

@Composable
private fun ItemWeather24ListColumn(hourly: Hourly) {
    val (isNow, formatFxTime) = WeatherUtils.hourlyTime(LocalContext.current, hourly.fxTime)
    val modifier = if (isNow) Modifier.itemSelected() else Modifier

    Column(
        modifier = modifier
            .padding(horizontal = 5.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatFxTime,
            fontSize = 15.sp,
            color = Color(0xFF000000),
        )
        WeatherImage(icon = hourly.icon)
        Text(
            text = stringResource(R.string.weather_temp_value, hourly.temp),
            fontSize = 15.sp,
            color = Color(0xFF000000),
            modifier = Modifier
                .padding(top = 4.dp)
        )
    }
}

@Composable
private fun ItemWeather24HTrend(modifier: Modifier, hourly24: List<Hourly>) {

    LazyRow(
        modifier = modifier.fillMaxWidth(),
    ) {
        item {
            ItemA(hourly24)
        }
    }
}


@Composable
private fun ItemA(hourly24: List<Hourly>) {

    val textPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        isDither = true
        textAlign = android.graphics.Paint.Align.CENTER
    }
    textPaint.color = android.graphics.Color.parseColor("#FF666666")
    textPaint.textSize = 36.0f
    val fontMetrics = textPaint.fontMetrics
    val top = fontMetrics.top
    val bottom = fontMetrics.bottom
    val textHeight = bottom - top

    val context = LocalContext.current
    var offsetX by remember { mutableStateOf(0f) }
    val itemWidth = 50.0f * 3
    Canvas(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 8.dp)
            .wrapContentWidth()
            .background(Color.LightGray)
            .height(100.dp)
//            .offset { IntOffset(offsetX.roundToInt(), 0) }
//            .draggable(
//                orientation = Orientation.Horizontal,
//                state = rememberDraggableState { delta ->
//                    offsetX += delta
//                }
//            )
    ) {

        val width = size.width
        val height = size.height
        hourly24.forEachIndexed { index, hourly ->
            val (isNow, formatFxTime) = WeatherUtils.hourlyTime(context, hourly.fxTime)
            drawIntoCanvas {
                val nativeCanvas = it.nativeCanvas
                nativeCanvas.drawText(
                    formatFxTime, index * itemWidth, height - textHeight / 2.0f,
                    textPaint
                )
            }
        }

    }
}