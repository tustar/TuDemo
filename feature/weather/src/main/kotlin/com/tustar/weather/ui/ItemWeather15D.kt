package com.tustar.weather.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.NativePaint
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tustar.data.source.remote.AirDaily
import com.tustar.data.source.remote.WeatherDaily
import com.tustar.weather.R
import com.tustar.weather.util.TrendSwitchMode

@Composable
fun ItemWeather15D(daily15D: List<WeatherDaily>, air5D: List<AirDaily>, mode15D: TrendSwitchMode) {
    ItemWeatherSwitch(
        titleId = R.string.weather_15d_forecast,
        trendMode = mode15D,
        list = { modifier ->
            ItemWeather15DList(modifier, daily15D, air5D)
        },
        trend = { modifier ->
            ItemWeather15DTrend(modifier, daily15D, air5D)
        },
    )
}

@Composable
private fun ItemWeather15DList(
    modifier: Modifier, daily15D: List<WeatherDaily>,
    air5D: List<AirDaily>,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier,
    ) {
        daily15D.forEachIndexed { index, weatherDaily ->
            ListDayInfo(weatherDaily = weatherDaily, airDaily = air5D.getOrNull(index))
        }
    }
}

@Composable
private fun ListDayInfo(weatherDaily: WeatherDaily, airDaily: AirDaily?) {
    val (date, week, isToday) = WeatherUtils.dateWeek(LocalContext.current, weatherDaily.fxDate)
    val modifier = if (isToday) Modifier.itemSelected() else Modifier

    Row(
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
    ) {
        // 1 date & week
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = date,
                fontSize = 13.sp,
                color = Color(0xFF666666),
            )
            Text(
                text = week,
                fontSize = 16.sp,
                color = Color(0xFF000000),
            )
        }

        // 2 day icon
        WeatherImage(icon = weatherDaily.iconDay)

        // 3 text & temp
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = WeatherUtils.dailyText(
                    context = LocalContext.current,
                    weatherDaily = weatherDaily
                ),
                fontSize = 13.sp,
                color = Color(0xFF666666),
            )
            Text(
                text = stringResource(
                    R.string.weather_temp_min_max,
                    weatherDaily.tempMin,
                    weatherDaily.tempMax
                ),
                fontSize = 15.sp,
                color = Color(0xFF000000),
            )
        }

        // 4 night icon
        WeatherImage(icon = weatherDaily.iconNight)

        // 5 wind & aqi
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(
                    id = R.string.weather_wind_dir_value,
                    weatherDaily.windDirDay,
                    weatherDaily.windScaleDay
                ),
                fontSize = 13.sp,
                color = Color(0xFF666666),
            )
            WeatherAqi(airDaily = airDaily)
        }
    }
}

@Composable
private fun ItemWeather15DTrend(
    modifier: Modifier, daily15D: List<WeatherDaily>,
    air5D: List<AirDaily>,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier,
    ) {
//        daily15D.forEachIndexed { index, weatherDaily ->
//
//        }
        val maxTemp = daily15D.maxOf { it.tempMax }
        val minTemp = daily15D.minOf { it.tempMin }

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
        val radius = 9.0f

        itemsIndexed(items = daily15D, itemContent = { index, current ->
            val (date, week, isToday) = WeatherUtils.dateWeek(
                LocalContext.current,
                current.fxDate,
                isList = false
            )
            val modifier = if (isToday) Modifier.itemSelected() else Modifier

            Column(
                modifier = modifier
                    .padding(horizontal = 4.dp, vertical = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TrendDayInfoTop(week, date, current)

                TrendDayInfoCenter(
                    current, daily15D, index, textHeight,
                    maxTemp, minTemp, radius, textPaint
                )

                TrendDayInfoBottom(current, air5D.getOrNull(index))
            }
        })
    }
}

@Composable
private fun TrendDayInfoCenter(
    current: WeatherDaily,
    daily15D: List<WeatherDaily>,
    index: Int,
    textHeight: Float,
    maxTemp: Int,
    minTemp: Int,
    radius: Float,
    textPaint: NativePaint
) {
    val highTemp = stringResource(R.string.weather_temp_value, current.tempMax)
    val lowerTemp = stringResource(R.string.weather_temp_value, current.tempMin)
    val strokeWidth = 6.0f
    val highColor = Color(0xFFFF7200)
    val lowerColor = Color(0xFF00A368)
    val prev = daily15D.getOrNull(index - 1)
    //
    Canvas(
        modifier = Modifier
            .width(30.dp)
            .height(140.dp)
    ) {
        val width = size.width
        val height = size.height - 2 * textHeight


        val ratio = height / (maxTemp - minTemp)
        val cx = width / 2.0f
        val highCy = calCy(maxTemp, current.tempMax, ratio, textHeight, radius)
        val highCenter = Offset(cx, highCy)
        drawIntoCanvas {
            val nativeCanvas = it.nativeCanvas
            nativeCanvas.drawText(
                highTemp, cx, highCy - radius - textHeight / 2.0f,
                textPaint
            )
        }
        drawCircle(
            color = highColor,
            radius = radius,
            center = highCenter,
        )

        val lowCy = calCy(maxTemp, current.tempMin, ratio, textHeight, radius)
        val lowCenter = Offset(cx, lowCy)
        drawCircle(
            color = lowerColor,
            radius = radius,
            center = lowCenter,
        )
        drawIntoCanvas {
            val nativeCanvas = it.nativeCanvas
            nativeCanvas.drawText(
                lowerTemp, cx, lowCy + radius + textHeight,
                textPaint
            )
        }

        val padding = 8 * density
        prev?.let {
            val prevHighCy = calCy(maxTemp, it.tempMax, ratio, textHeight, radius)
            val prevHighCenter = Offset(-width - padding, prevHighCy)
            drawLine(color = highColor, prevHighCenter, highCenter, strokeWidth)

            val prevLowCy = calCy(maxTemp, it.tempMin, ratio, textHeight, radius)
            val prevLowCenter = Offset(-width - padding, prevLowCy)
            drawLine(color = lowerColor, prevLowCenter, lowCenter, strokeWidth)
        }
    }
}

private fun calCy(
    maxTemp: Int,
    temp: Int,
    ratio: Float,
    textHeight: Float,
    radius: Float
): Float {
    return (maxTemp - temp) * ratio + textHeight + radius
}

@Composable
private fun TrendDayInfoTop(
    week: String,
    date: String,
    weatherDaily: WeatherDaily
) {
    Text(
        text = week,
        fontSize = 16.sp,
        color = Color(0xFF000000),
    )
    Text(
        text = date,
        fontSize = 12.sp,
        color = Color(0xFF666666),
        modifier = Modifier
            .padding(top = 6.dp),
    )

    Text(
        text = weatherDaily.textDay,
        fontSize = 15.sp,
        color = Color(0xFF000000),
        modifier = Modifier
            .padding(top = 6.dp),
    )

    WeatherImage(
        modifier = Modifier.padding(vertical = 4.dp),
        icon = weatherDaily.iconDay
    )
}

@Composable
private fun TrendDayInfoBottom(
    weatherDaily: WeatherDaily,
    airDaily: AirDaily?
) {
    WeatherImage(
        modifier = Modifier.padding(vertical = 4.dp),
        icon = weatherDaily.iconNight
    )

    Text(
        text = weatherDaily.textNight,
        fontSize = 15.sp,
        color = Color(0xFF000000),
    )
    Text(
        text = weatherDaily.windDirDay,
        fontSize = 13.sp,
        color = Color(0xFF666666),
        modifier = Modifier
            .padding(top = 6.dp),
    )
    Text(
        text = stringResource(id = R.string.weather_wind_value, weatherDaily.windScaleDay),
        fontSize = 13.sp,
        color = Color(0xFF666666),
        modifier = Modifier
            .padding(top = 6.dp, bottom = 4.dp),
    )
    WeatherAqi(modifier = Modifier.padding(bottom = 4.dp), airDaily = airDaily)
}