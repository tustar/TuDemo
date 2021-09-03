package com.tustar.weather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
    Box {
        Image(
            painter = painterResource(id = R.drawable.bg_0_d),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )

        LazyColumn() {
            item {
                ItemWeatherHeader(weather.weatherNow, weather.warning, weather.airNow)
            }
            item {
                ItemWeatherNow(weather.weatherNow)
            }
            item {
                ItemWeather24h(weather.hourly24h)
            }
            item {
                ItemWeather15d(weather.daily15d)
            }
//            item {
//                ItemWeatherIndices(weather.indices)
//            }
        }
    }
}

