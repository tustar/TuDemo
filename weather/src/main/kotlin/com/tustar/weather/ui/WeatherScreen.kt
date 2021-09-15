package com.tustar.weather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.systemuicontroller.SystemUiController
import com.tustar.data.Weather
import com.tustar.weather.R

@Composable
fun WeatherScreen(
    systemUiController: SystemUiController,
    weather: Weather,
) {
    SideEffect {
        systemUiController.setStatusBarColor(color = Color.Transparent)
    }
    Column() {

    }
    Box {
        Image(
            painter = painterResource(id = R.drawable.bg_0_d),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )

        LazyColumn {
            item {
                ItemWeatherHeader(weather.weatherNow, weather.warning, weather.airNow)
            }
            item {
                ItemWeatherNow(weather.weatherNow)
            }
            item {
                ItemWeather3d(weather.daily15d.subList(0, 3), weather.air5d.subList(0, 3))
            }
            item {
                ItemWeather24h(weather.hourly24h)
            }
            item {
                ItemWeather15d(weather.daily15d, weather.air5d)
            }
            item {
                ItemWeatherSun(weather.daily15d[0])
            }
            item {
                ItemWeatherIndices(weather.indices)
            }
            item {
                ItemWeatherSources()
            }
            item {
                Spacer(modifier = Modifier.navigationBarsHeight())
            }
        }
    }
}

