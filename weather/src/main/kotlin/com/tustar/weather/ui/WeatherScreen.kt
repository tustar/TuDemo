package com.tustar.weather.ui

import android.content.Context
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.pager.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.accompanist.systemuicontroller.SystemUiController
import com.tustar.data.Weather
import com.tustar.weather.Location
import com.tustar.weather.R
import com.tustar.weather.WeatherPrefs
import kotlinx.coroutines.delay
import kotlin.reflect.KFunction2

@OptIn(ExperimentalPagerApi::class)
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
    val locations = mutableListOf<Location>(weatherPrefs.locate).apply {
        val cities = weatherPrefs.citiesList
        if (!cities.isNullOrEmpty()) {
            addAll(cities)
        }
    }

    val pagerState = rememberPagerState()
    viewModel.requestWeather(LocalContext.current, locations[pagerState.currentPage])
    Box {
        Image(
            painter = painterResource(id = R.drawable.bg_0_d),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )

        Column {
            TopBar(locations, pagerState)

            HorizontalPager(count = locations.size, state = pagerState) { page ->
                weather?.let {
                    weatherContent(it, weatherPrefs, viewModel::onList24h, viewModel::onList15d)
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun TopBar(
    locations: List<Location>,
    pagerState: PagerState
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, bottom = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.AddCircle,
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterStart)
                .padding(start = 16.dp),
            tint = Color.White,
        )

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier,
                text = locations[pagerState.currentPage].name,
                color = Color.White,
                fontSize = 16.sp,
            )
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 6.dp),
                activeColor = Color.White,
                inactiveColor = Color(0x66FFFFFF),
                indicatorWidth = 4.dp,
            )
        }
    }
}

@Composable
private fun weatherContent(
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
                    ItemWeather3d(weather.daily15d.subList(0, 3), weather.air5d.subList(0, 3))
                }
                item {
                    ItemWeather24h(weather.hourly24h, weatherPrefs.list24H, onList24h)
                }
                item {
                    ItemWeather15d(weather.daily15d, weather.air5d, weatherPrefs.list15D, onList15d)
                }
                item {
                    ItemWeatherSunrise(weather.daily15d[0])
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

