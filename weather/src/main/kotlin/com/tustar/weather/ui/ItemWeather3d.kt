package com.tustar.weather.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tustar.data.source.remote.AirDaily
import com.tustar.data.source.remote.WeatherDaily
import com.tustar.weather.R

@Composable
fun ItemWeather3d(weather3d: List<WeatherDaily>, air3d: List<AirDaily>) {
    Row(
        modifier = Modifier
            .itemBackground(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ItemWeather3dColumn(
            modifier = Modifier.weight(1f),
            dateId = R.string.weather_today,
            weatherDaily = weather3d[0],
            airDaily = air3d[0],
        )
        Divider(
            modifier = Modifier
                .width(0.5.dp)
                .height(60.dp),
            color = Color(0xCCFFFFFF),
        )
        ItemWeather3dColumn(
            modifier = Modifier.weight(1f),
            dateId = R.string.weather_tomorrow,
            weatherDaily = weather3d[1],
            airDaily = air3d[1],
        )
        Divider(
            modifier = Modifier
                .width(0.5.dp)
                .height(60.dp),
            color = Color(0xCCFFFFFF),
        )
        ItemWeather3dColumn(
            modifier = Modifier.weight(1f),
            dateId = R.string.weather_after_tomorrow,
            weatherDaily = weather3d[2],
            airDaily = air3d[2],
        )
    }
}

@Composable
private fun ItemWeather3dColumn(
    modifier: Modifier,
    @StringRes dateId: Int,
    weatherDaily: WeatherDaily,
    airDaily: AirDaily,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(
            text = stringResource(dateId),
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 3.dp),
        )
        ItemWeatherImage(icon = weatherDaily.iconDay)
        Text(
            text = WeatherHelper.dailyText(
                context = LocalContext.current,
                weatherDaily = weatherDaily
            ),
            fontSize = 15.sp,
            color = Color(0xFF666666),
            modifier = Modifier.padding(top = 5.dp),
        )
        Text(
            text = stringResource(
                R.string.weather_temp_min_max,
                weatherDaily.tempMin,
                weatherDaily.tempMax
            ),
            fontSize = 15.sp,
            color = Color.Black,
            modifier = Modifier.padding(top = 5.dp),
        )
        ItemWeatherAqi(airDaily = airDaily)
    }
}