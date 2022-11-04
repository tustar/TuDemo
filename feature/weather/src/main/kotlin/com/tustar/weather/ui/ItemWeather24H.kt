package com.tustar.weather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tustar.data.source.remote.Hourly
import com.tustar.weather.R


@Composable
fun ItemWeather24H(hourly24: List<Hourly>) {
    LazyRow(
        modifier = Modifier
            .padding(LocalWeatherSize.current.margin.dp)
            .background(Color(0x1A000000), RoundedCornerShape(5.dp))
            .padding(LocalWeatherSize.current.margin.dp),
        horizontalArrangement = Arrangement.spacedBy(LocalWeatherSize.current.margin.dp)
    ) {
        items(items = hourly24, itemContent = { hourly ->
            HourColumn(hourly = hourly)
        })
    }
}

@Composable
private fun HourColumn(hourly: Hourly) {
    val (isNow, formatFxTime) = WeatherUtils.hourlyTime(LocalContext.current, hourly.fxTime)

    Column(
        modifier = Modifier
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatFxTime,
            style = LocalWeatherSize.current.body1,
        )
        Image(
            painter = painterResource(
                id = WeatherUtils.weatherIconId(
                    context = LocalContext.current,
                    icon = hourly.icon
                )
            ),
            contentDescription = null,
            modifier = Modifier
                .width(LocalWeatherSize.current.imgWidth.dp)
                .padding(vertical = 15.dp)
        )
        Text(
            text = stringResource(R.string.weather_temp_value, hourly.temp),
            style = LocalWeatherSize.current.body1,
        )
    }
}
