package com.tustar.weather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.pager.*
import com.google.accompanist.systemuicontroller.SystemUiController
import com.tustar.data.Weather
import com.tustar.ktx.compose.tint
import com.tustar.weather.R
import com.tustar.weather.theme.WeatherTheme
import com.tustar.weather.util.TrendSwitchMode


@OptIn(ExperimentalPagerApi::class)
@Composable
fun WeatherScreen(
    systemUiController: SystemUiController,
    viewModel: WeatherViewModel,
    onManageCity: () -> Unit,
) {
    val weatherPrefs by viewModel.weatherPrefs.collectAsState()
    //
    val cities by viewModel.cities.collectAsState()
    val weather by viewModel.weather.collectAsState()
    //
    val locations = cities.values.toList()
    val pagerState = rememberPagerState()
    val current = locations[pagerState.currentPage]
    viewModel.requestWeather(LocalContext.current, current)
    //
    val bg = R.drawable.bg_0_d
    systemUiController.setStatusBarColor(color = Color.Transparent)
    WeatherTheme {
        Box {
            Image(
                painter = painterResource(id = bg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )

            Scaffold(
                backgroundColor = Color.Transparent,
                topBar = {
                    TopBar(current.name, pagerState, onManageCity)
                },
            ) {
                HorizontalPager(
                    count = locations.size,
                    state = pagerState,
                    modifier = Modifier,
                ) {
                    weather?.let {
                        weatherContent(
                            it.weather,
                            Pair(weatherPrefs.mode24H, viewModel::saveMode24H),
                            Pair(weatherPrefs.mode24H, viewModel::saveMode15D),
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun TopBar(
    city: String,
    pagerState: PagerState,
    onManageCity: () -> Unit,
) {
    Column(modifier = Modifier.background(Color.Transparent)) {
        Spacer(modifier = Modifier.statusBarsHeight())
        TopAppBar(
            title = {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                ) {
                    Text(
                        modifier = Modifier,
                        text = city,
                        color = Color.White,
                        fontSize = 20.sp,
                    )
                    HorizontalPagerIndicator(
                        pagerState = pagerState,
                        modifier = Modifier.padding(top = 6.dp),
                        activeColor = Color.White,
                        inactiveColor = Color(0x66FFFFFF),
                        indicatorWidth = 4.dp,
                    )
                }
            },
            actions = {
                val (interactionSource, tint) = tint()
                IconButton(
                    onClick = { onManageCity() },
                    interactionSource = interactionSource
                ) {
                    Icon(
                        imageVector = Icons.Filled.FormatListBulleted,
                        contentDescription = null,
                        tint = tint,
                    )
                }
            },
            elevation = 0.dp,
            backgroundColor = Color.Transparent
        )
    }
}

@Composable
private fun weatherContent(weather: Weather, mode24H: TrendSwitchMode, mode15D: TrendSwitchMode) {
    val listState = rememberLazyListState()
    LazyColumn(state = listState) {
        item {
            ItemWeatherHeader(
                weather.weatherNow, weather.warning, weather.airNow
            )
        }
        item {
            ItemWeatherNow(weather.weatherNow)
        }
        item {
            ItemWeather3D(weather.daily15D.subList(0, 3), weather.air5D.subList(0, 3))
        }
        item {
            ItemWeather24H(weather.hourly24H, mode24H)
        }
        item {
            ItemWeather15D(weather.daily15D, weather.air5D, mode15D)
        }
        item {
            ItemWeatherSunrise(weather.daily15D[0])
        }
        item {
            ItemWeatherIndices1D(weather.indices1D)
        }
        item {
            ItemWeatherSources()
        }
        item {
            Spacer(modifier = Modifier.navigationBarsHeight())
        }
    }
}

