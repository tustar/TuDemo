package com.tustar.weather.ui

import android.content.Context
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.accompanist.systemuicontroller.SystemUiController
import com.tustar.data.Weather
import com.tustar.weather.R
import com.tustar.weather.WeatherPrefs
import kotlinx.coroutines.delay
import kotlin.reflect.KFunction2

@Composable
fun WeatherScreen(
    systemUiController: SystemUiController,
    viewModel: WeatherViewModel,
) {
    SideEffect {
        systemUiController.setStatusBarColor(color = Color.Transparent)
    }

    val weather by viewModel.weather.collectAsState()
    val weatherPrefs by viewModel.weatherPrefs.collectAsState()
    viewModel.weatherPrefs(LocalContext.current)
    weather?.let {
        WeatherContent(it, weatherPrefs, viewModel::onList24h, viewModel::onList15d)
    }
}

@Composable
fun WeatherContent(
    weather: Weather,
    weatherPrefs: WeatherPrefs,
    onList24h: KFunction2<Context, Boolean, Unit>,
    onList15d: KFunction2<Context, Boolean, Unit>,
) {
    var refreshing by remember { mutableStateOf(false) }
    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(2000)
            refreshing = false
        }
    }

    Box {
        Image(
            painter = painterResource(id = R.drawable.bg_0_d),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = refreshing),
            onRefresh = { refreshing = true },
            indicator = { state, trigger ->
                GlowIndicator(
                    swipeRefreshState = state,
                    refreshTriggerDistance = trigger
                )
            },
        ) {
            val listState = rememberLazyListState()
            var rising by remember { mutableStateOf(false) }
            if (!rising) {
                rising = listState.layoutInfo.visibleItemsInfo.any { it.index == 5 }
            }
            val risingPair = Pair<Boolean, (Boolean) -> Unit>(rising, { rising = it })
            LazyColumn(state = listState) {
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
                    ItemWeather24h(weather.hourly24h, weatherPrefs.list24H, onList24h)
                }
                item {
                    ItemWeather15d(weather.daily15d, weather.air5d, weatherPrefs.list15D, onList15d)
                }
                item {
                    ItemWeatherSunrise(weather.daily15d[0], risingPair)
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
}

@Composable
fun GlowIndicator(
    swipeRefreshState: SwipeRefreshState,
    refreshTriggerDistance: Dp,
    color: Color = MaterialTheme.colors.primary,
) {
    Box(
        Modifier
            .drawWithCache {
                onDrawBehind {
                    val distance = refreshTriggerDistance.toPx()
                    val progress = (swipeRefreshState.indicatorOffset / distance).coerceIn(0f, 1f)
                    // We draw a translucent glow
                    val brush = Brush.verticalGradient(
                        0f to color.copy(alpha = 0.45f),
                        1f to color.copy(alpha = 0f)
                    )
                    // And fade the glow in/out based on the swipe progress
                    drawRect(brush = brush, alpha = FastOutSlowInEasing.transform(progress))
                }
            }
            .fillMaxWidth()
            .height(72.dp)
    ) {
        if (swipeRefreshState.isRefreshing) {
            // If we're refreshing, show an indeterminate progress indicator
            LinearProgressIndicator(Modifier.fillMaxWidth())
        } else {
            // Otherwise we display a determinate progress indicator with the current swipe progress
            val trigger = with(LocalDensity.current) { refreshTriggerDistance.toPx() }
            val progress = (swipeRefreshState.indicatorOffset / trigger).coerceIn(0f, 1f)
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

