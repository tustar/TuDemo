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
fun ItemWeather3D(weather3D: List<WeatherDaily>, air3D: List<AirDaily>) {
    Row(
        modifier = Modifier
            .itemBackground(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ItemWeather3DColumn(
            modifier = Modifier.weight(1f),
            dateId = R.string.weather_today,
            weatherDaily = weather3D[0],
            airDaily = air3D[0],
        )
        Divider(
            modifier = Modifier
                .width(0.5.dp)
                .height(60.dp),
            color = Color(0xCCFFFFFF),
        )
        ItemWeather3DColumn(
            modifier = Modifier.weight(1f),
            dateId = R.string.weather_tomorrow,
            weatherDaily = weather3D[1],
            airDaily = air3D[1],
        )
        Divider(
            modifier = Modifier
                .width(0.5.dp)
                .height(60.dp),
            color = Color(0xCCFFFFFF),
        )
        ItemWeather3DColumn(
            modifier = Modifier.weight(1f),
            dateId = R.string.weather_after_tomorrow,
            weatherDaily = weather3D[2],
            airDaily = air3D[2],
        )
    }
}

@Composable
private fun ItemWeather3DColumn(
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
        WeatherImage(icon = weatherDaily.iconDay)
        Text(
            text = WeatherUtils.dailyText(
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
        WeatherAqi(airDaily = airDaily)
    }
}