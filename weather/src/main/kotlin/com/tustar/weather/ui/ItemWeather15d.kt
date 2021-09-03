package com.tustar.weather.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.tustar.data.source.remote.WeatherDaily
import com.tustar.weather.R
import com.tustar.weather.R.string

@Composable
fun ItemWeather15d(daily15d: List<WeatherDaily>) {
    ConstraintLayout(
        modifier = Modifier
            .itemBackground()
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        val (title, content) = createRefs()

        Text(
            text = stringResource(string.weather_15d_forecast),
            fontSize = 15.sp,
            color = Color(0xFF000000),
            modifier = Modifier
                .padding(vertical = 4.dp)
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Column(modifier = Modifier
            .padding(vertical = 4.dp)
            .constrainAs(content) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            daily15d.forEach {
                DayInfo(daily = it)
            }
        }
    }
}

@Composable
private fun DayInfo(daily: WeatherDaily) {
    val (date, week, isToday) = WeatherHelper.dateWeek(daily.fxDate)
    val modifier = if (isToday) {
        Modifier.border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
    } else {
        Modifier
    }

    Row(
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 4.dp),
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = date,
                fontSize = 14.sp,
                color = Color(0xFF666666),
            )
            Text(
                text = week,
                fontSize = 15.sp,
                color = Color(0xFF000000),
            )
        }

        Text(
            text = stringResource(R.string.weather_temp_min_max, daily.tempMin, daily.tempMax),
            fontSize = 15.sp,
            color = Color(0xFF000000),
            modifier = Modifier.weight(1f)
        )
    }
}