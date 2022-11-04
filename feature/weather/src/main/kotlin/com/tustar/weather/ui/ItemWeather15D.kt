package com.tustar.weather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tustar.data.source.remote.AirDaily
import com.tustar.data.source.remote.WeatherDaily
import com.tustar.weather.R

@Composable
fun ItemWeather15D(daily15D: List<WeatherDaily>, air5D: List<AirDaily>) {
    ItemWeatherTitle(titleId = R.string.weather_forecast_15d) {
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
            .padding(horizontal = 4.dp, vertical = 12.dp)
    ) {
        // 1 date & week
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1.0f),
        ) {
            Text(
                text = date,
                style = LocalWeatherSize.current.body2,
            )
            Text(
                text = week,
                style = LocalWeatherSize.current.body1,
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
                style = LocalWeatherSize.current.body2,
            )
            Text(
                text = stringResource(
                    R.string.weather_temp_min_max,
                    weatherDaily.tempMin,
                    weatherDaily.tempMax
                ),
                style = LocalWeatherSize.current.body1,
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
                style = LocalWeatherSize.current.body2,
            )
            WeatherAqi(airDaily = airDaily)
        }
    }
}


@Composable
fun WeatherAqi(modifier: Modifier = Modifier, airDaily: AirDaily?) {
    Text(
        text = airDaily?.aqi ?: "NA",
        style = LocalWeatherSize.current.body1,
        textAlign = TextAlign.Center,
        modifier = modifier
            .padding(top = 2.dp)
            .width(30.dp)
            .background(WeatherUtils.aqiColor(airDaily?.aqi?.toInt() ?: 0), RoundedCornerShape(2.5.dp)),
    )
}