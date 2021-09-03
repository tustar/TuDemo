package com.tustar.weather.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
    Row(modifier = Modifier.itemBackground()) {
        ItemWeatherNowColumn(
            modifier = Modifier.weight(1f),
            keyId = R.string.weather_feelsLike,
            valueId = R.string.weather_temp_value,
            value = weatherNow.feelsLike
        )
        ItemWeatherNowColumn(
            modifier = Modifier.weight(1f),
            keyId = R.string.weather_humidity,
            valueId = R.string.weather_humidity_value,
            value = weatherNow.humidity
        )
        ItemWeatherNowColumn(
            modifier = Modifier.weight(1f),
            keyId = R.string.weather_windScale,
            valueId = R.string.weather_windScale_value,
            value = weatherNow.windScale
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
fun ItemWeatherNowColumn(
    modifier: Modifier,
    @StringRes keyId: Int,
    @StringRes valueId: Int,
    value: Int
) {
    Column(
        modifier = modifier.padding(top = 4.dp, bottom = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(valueId, value),
            fontSize = 15.sp,
            color = Color(0xFF000000),
        )
        Text(
            text = stringResource(keyId),
            fontSize = 14.sp,
            color = Color(0xFF666666),
        )
    }
}