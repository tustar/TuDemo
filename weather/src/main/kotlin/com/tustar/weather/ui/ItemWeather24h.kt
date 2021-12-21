package com.tustar.weather.ui

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.tustar.data.source.remote.Hourly
import com.tustar.weather.R
import kotlin.math.roundToInt
import kotlin.reflect.KFunction2

@Composable
fun ItemWeather24h(
    hourly24: List<Hourly>,
    list24h: Boolean,
    onList24h: KFunction2<Context, Boolean, Unit>,
) {
    val context = LocalContext.current
    var isList by remember { mutableStateOf(list24h) }
    ConstraintLayout(
        modifier = Modifier
            .itemBackground()
            .fillMaxWidth()
    ) {
        val (switch, title, content) = createRefs()

        WeatherSwitch(
            checked = isList,
            onCheckedChange = {
                isList = it
                onList24h(context, isList)
            },
            modifier = Modifier
                .constrainAs(switch) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .padding(start = 4.dp),
        )

        ItemWeatherTopBar(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            id = R.string.weather_24h_forecast
        )

        if (isList) {
            ItemWeather24hList(
                modifier = Modifier
                    .constrainAs(content) {
                        top.linkTo(title.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                hourly24 = hourly24
            )
        } else {
            ItemWeather24hTrend(
                modifier = Modifier
                    .constrainAs(content) {
                        top.linkTo(title.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                hourly24 = hourly24
            )
        }
    }
}

@Composable
private fun ItemWeather24hList(modifier: Modifier, hourly24: List<Hourly>) {
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
    val (isNow, formatFxTime) = WeatherHelper.hourlyTime(LocalContext.current, hourly.fxTime)
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
        ItemWeatherImage(icon = hourly.icon)
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
private fun ItemWeather24hTrend(modifier: Modifier, hourly24: List<Hourly>) {

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
            val (isNow, formatFxTime) = WeatherHelper.hourlyTime(context, hourly.fxTime)
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