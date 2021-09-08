package com.tustar.weather.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.tustar.data.source.remote.AirDaily
import com.tustar.data.source.remote.WeatherDaily
import com.tustar.weather.R

@Composable
fun ItemWeather15d(daily15d: List<WeatherDaily>, air5d: List<AirDaily>) {
    ConstraintLayout(
        modifier = Modifier
            .itemBackground()
            .fillMaxWidth(),
    ) {
        val (title, content) = createRefs()

        ItemWeatherTopBar(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            id = R.string.weather_15d_forecast
        )

        Column(modifier = Modifier
            .padding(vertical = 4.dp)
            .constrainAs(content) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            daily15d.forEachIndexed { index, weatherDaily ->
                DayInfo(weatherDaily = weatherDaily, airDaily = air5d.getOrNull(index))
            }
        }
    }
}

@Composable
private fun DayInfo(weatherDaily: WeatherDaily, airDaily: AirDaily?) {
    val (date, week, isToday) = WeatherHelper.dateWeek(LocalContext.current, weatherDaily.fxDate)
    val modifier = if (isToday) Modifier.itemSelected() else Modifier

    Row(
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 8.dp)
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
                fontSize = 15.sp,
                color = Color(0xFF000000),
            )
        }

        // 2 day icon
        ItemWeatherImage(icon = weatherDaily.iconDay)

        // 3 text & temp
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = WeatherHelper.dailyText(
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
        ItemWeatherImage(icon = weatherDaily.iconNight)

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
            ItemWeatherAqi(airDaily = airDaily)
        }
    }
}