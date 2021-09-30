package com.tustar.weather.ui

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import kotlin.reflect.KFunction2

@Composable
fun ItemWeather15d(
    daily15d: List<WeatherDaily>,
    air5d: List<AirDaily>,
    list15d: Boolean,
    onList15d: KFunction2<Context, Boolean, Unit>,
) {
    val context = LocalContext.current
    var isList by remember { mutableStateOf(list15d) }
    ConstraintLayout(
        modifier = Modifier
            .itemBackground()
            .fillMaxWidth(),
    ) {
        val (switch, title, content) = createRefs()

        WeatherSwitch(
            checked = isList,
            onCheckedChange = {
                isList = it
                onList15d(context, isList)
            },
            modifier = Modifier
                .constrainAs(switch) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .padding(start = 4.dp),
        )

        ItemWeatherTopBar(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            id = R.string.weather_15d_forecast
        )

        if (isList) {
            ItemWeather15dList(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .constrainAs(content) {
                        top.linkTo(title.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                daily15d, air5d
            )
        } else {
            ItemWeather15dTrend(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .constrainAs(content) {
                        top.linkTo(title.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                daily15d, air5d
            )
        }
    }
}

@Composable
private fun ItemWeather15dList(
    modifier: Modifier, daily15d: List<WeatherDaily>,
    air5d: List<AirDaily>,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier,
    ) {
        daily15d.forEachIndexed { index, weatherDaily ->
            DayInfo(weatherDaily = weatherDaily, airDaily = air5d.getOrNull(index))
        }
    }
}


@Composable
private fun DayInfo(weatherDaily: WeatherDaily, airDaily: AirDaily?) {
    val (date, week, isToday) = WeatherHelper.dateWeek(LocalContext.current, weatherDaily.fxDate)
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

@Composable
private fun ItemWeather15dTrend(
    modifier: Modifier, daily15d: List<WeatherDaily>,
    air5d: List<AirDaily>,
) {

}