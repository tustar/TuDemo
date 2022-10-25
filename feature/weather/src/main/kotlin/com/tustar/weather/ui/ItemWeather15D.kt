package com.tustar.weather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tustar.data.source.remote.AirDaily
import com.tustar.data.source.remote.WeatherDaily
import com.tustar.utils.Logger
import com.tustar.weather.R

@Composable
fun ItemWeather15D(daily15D: List<WeatherDaily>, air5D: List<AirDaily>) {
    ItemWeatherTitle(titleId = R.string.weather_15d_forecast) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier,
        ) {
            daily15D.forEachIndexed { index, weatherDaily ->
                DayRow(weatherDaily = weatherDaily, airDaily = air5D.getOrNull(index))
            }
        }
    }
}

@Composable
private fun DayRow(weatherDaily: WeatherDaily, airDaily: AirDaily?) {
    val (date, week, isToday) = WeatherUtils.dateWeek(LocalContext.current, weatherDaily.fxDate)

    Row(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 16.dp)
    ) {
        // 1 date & week
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = date,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFF0F0ED),
            )
            Text(
                text = week,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
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
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFF0F0ED),
            )
            Text(
                text = stringResource(
                    R.string.weather_temp_min_max,
                    weatherDaily.tempMin,
                    weatherDaily.tempMax
                ),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
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
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFF0F0ED),
            )
            WeatherAqi(airDaily = airDaily)
        }
    }
}


@Composable
fun WeatherAqi(modifier: Modifier = Modifier, airDaily: AirDaily?) {
    Text(
        text = airDaily?.aqi ?: "NA",
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
        color = Color.White,
        modifier = modifier
            .padding(top = 2.dp)
            .width(30.dp)
            .background(WeatherUtils.aqiColor(airDaily?.aqi?.toInt() ?: 0), RoundedCornerShape(2.5.dp)),
    )
}