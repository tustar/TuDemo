package com.tustar.weather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
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
    LazyRow(modifier = Modifier.padding(top = 24.dp)) {
        items(items = hourly24, itemContent = { hourly ->
            HourColumn(hourly = hourly)
        })
    }
}

@Composable
private fun HourColumn(hourly: Hourly) {
    val (isNow, formatFxTime) = WeatherUtils.hourlyTime(LocalContext.current, hourly.fxTime)
    val modifier = if (isNow) Modifier.itemSelected() else Modifier

    Column(
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatFxTime,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
        )
        Image(
            painter = painterResource(
                id = WeatherUtils.weatherIconId(
                    context = LocalContext.current,
                    icon = hourly.icon
                )
            ),
            contentDescription = null,
            modifier = modifier
                .padding(vertical = 15.dp)
        )
        Text(
            text = stringResource(R.string.weather_temp_value, hourly.temp),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
    }
}
