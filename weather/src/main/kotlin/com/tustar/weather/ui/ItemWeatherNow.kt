package com.tustar.weather.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tustar.data.source.remote.WeatherNow
import com.tustar.weather.R

@Composable
fun ItemWeatherNow(weatherNow: WeatherNow) {
    Row(
        modifier = Modifier
            .itemBackground(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ItemWeatherNowColumn(
            modifier = Modifier.weight(1f),
            keyId = R.string.weather_feelsLike,
            valueId = R.string.weather_temp_value,
            value = weatherNow.feelsLike
        )
        Divider(
            modifier = Modifier
                .width(0.5.dp)
                .height(30.dp),
            color = Color(0xCCFFFFFF),
        )
        ItemWeatherNowColumn(
            modifier = Modifier.weight(1f),
            keyId = R.string.weather_humidity,
            valueId = R.string.weather_humidity_value,
            value = weatherNow.humidity
        )
        Divider(
            modifier = Modifier
                .width(0.5.dp)
                .height(30.dp),
            color = Color(0xCCFFFFFF),
        )
        ItemWeatherNowColumn(
            modifier = Modifier.weight(1f),
            keyId = R.string.weather_windScale,
            valueId = R.string.weather_wind_value,
            value = weatherNow.windScale
        )
        Divider(
            modifier = Modifier
                .width(0.5.dp)
                .height(30.dp),
            color = Color(0xCCFFFFFF),
        )
        ItemWeatherNowColumn(
            modifier = Modifier.weight(1f),
            keyId = R.string.weather_pressure,
            valueId = R.string.weather_pressure_value,
            value = weatherNow.pressure
        )
    }
}

@Composable
private fun ItemWeatherNowColumn(
    modifier: Modifier,
    @StringRes keyId: Int,
    @StringRes valueId: Int,
    value: Int
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 3.dp),
            text = stringResource(valueId, value),
            fontSize = 16.sp,
            color = Color(0xFF000000),
        )
        Text(
            text = stringResource(keyId),
            fontSize = 13.sp,
            color = Color(0xFF666666),
        )
    }
}