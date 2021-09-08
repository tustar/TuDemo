package com.tustar.weather.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.tustar.data.source.remote.Hourly
import com.tustar.weather.R

@Composable
fun ItemWeather24h(hourly24: List<Hourly>) {
    ConstraintLayout(
        modifier = Modifier
            .itemBackground()
            .fillMaxWidth()
    ) {
        val (title, content) = createRefs()

        ItemWeatherTopBar(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            id = R.string.weather_24h_forecast
        )

        ItemWeather24List(
            modifier = Modifier
                .constrainAs(content) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            hourly24 = hourly24
        )
    }
}

@Composable
private fun ItemWeather24List(modifier: Modifier, hourly24: List<Hourly>) {
    LazyRow(
        modifier = modifier,
    ) {
        items(items = hourly24, itemContent = { hourly ->
            ItemWeather24ListColumn(hourly = hourly)
        })
    }
}

@Composable
private fun ItemWeather24ListColumn(hourly: Hourly) {
    val (isNow, formatFxTime) = WeatherHelper.hourlyTime(LocalContext.current, hourly.fxTime)
    val modifier = if (isNow) Modifier.itemSelected() else Modifier

    Column(
        modifier = modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatFxTime,
            fontSize = 15.sp,
            color = Color(0xFF000000),
        )
        ItemWeatherImage(icon = hourly.icon)
        Text(
            text = stringResource(R.string.weather_temp_value, hourly.temp),
            fontSize = 15.sp,
            color = Color(0xFF000000),
            modifier = Modifier
                .padding(top = 4.dp)
        )
    }
}

@Composable
private fun ItemWeather24Trend(modifier: Modifier, hourly24: List<Hourly>) {
    LazyRow(modifier = modifier) {
        items(items = hourly24, itemContent = { hourly ->
            ItemWeather24ListColumn(hourly = hourly)
        })
    }
}