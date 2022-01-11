package com.tustar.weather.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.pager.*
import com.google.accompanist.systemuicontroller.SystemUiController
import com.tustar.data.Weather
import com.tustar.weather.Location
import com.tustar.weather.R
import com.tustar.weather.WeatherPrefs
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.reflect.KFunction2

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WeatherScreen(
    systemUiController: SystemUiController,
    viewModel: WeatherViewModel,
) {
    val weather by viewModel.weather.collectAsState()
    val weatherPrefs by viewModel.weatherPrefs.collectAsState()
    val current by viewModel.current.collectAsState()
    val locations by viewModel.cities.collectAsState()
    viewModel.weatherPrefs(LocalContext.current)
    //
    val pagerState = rememberPagerState()
    val values = locations.values.toList()
    viewModel.requestWeather(LocalContext.current, values[pagerState.currentPage])
    //
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val managerCity = { scope.launch { scaffoldState.drawerState.open() } }
    val closeDrawer: () -> Unit = {
        scope.launch {
            val page = values.indexOf(current)
            pagerState.scrollToPage(page)
            scaffoldState.drawerState.close()
        }
    }

    val bg = R.drawable.bg_0_d
    systemUiController.setStatusBarColor(color = Color.Transparent)
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
            scaffoldState = scaffoldState,
            topBar = { TopBar(values, pagerState, managerCity) },
            drawerContent = {
                Drawer(viewModel = viewModel, closeDrawer = closeDrawer)
            }
        ) {
            HorizontalPager(
                count = locations.size, state = pagerState,
                modifier = Modifier,
            ) {
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
    pagerState: PagerState,
    manageCity: () -> Job,
) {
    Column(modifier = Modifier.background(Color.Transparent)) {
        Spacer(modifier = Modifier.statusBarsHeight())
        TopAppBar(
            title = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                ) {
                    Text(
                        modifier = Modifier,
                        text = locations[pagerState.currentPage].name,
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
            navigationIcon = {
                IconButton(onClick = { manageCity() }) {
                    Icon(
                        imageVector = Icons.Filled.FormatListBulleted,
                        contentDescription = null,
                        tint = Color.White,
                    )
                }
            },
            elevation = 0.dp,
            backgroundColor = Color.Transparent
        )
    }
}

@Composable
private fun weatherContent(
    weather: Weather,
    weatherPrefs: WeatherPrefs,
    onList24h: KFunction2<Context, Boolean, Unit>,
    onList15d: KFunction2<Context, Boolean, Unit>,
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

