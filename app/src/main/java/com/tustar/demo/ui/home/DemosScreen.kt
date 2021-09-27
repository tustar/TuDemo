package com.tustar.demo.ui.home

import android.text.format.DateUtils
import android.util.TimeUtils
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.SystemUiController
import com.tustar.data.DemoItem
import com.tustar.data.Weather
import com.tustar.demo.R
import com.tustar.demo.ui.MainViewModel
import com.tustar.demo.ui.SectionView
import com.tustar.demo.ui.theme.DemoTheme
import com.tustar.demo.ui.topAppBar
import com.tustar.demo.util.Logger
import java.util.concurrent.TimeUnit

@Composable
fun DemosScreen(
    systemUiController: SystemUiController,
    viewModel: MainViewModel,
    onDemoClick: (Int) -> Unit,
    onWeatherClick: () -> Unit,
) {
    val statusBarColor = DemoTheme.colors.primarySurface
    SideEffect {
        systemUiController.setStatusBarColor(statusBarColor)
    }
    //
    val context = LocalContext.current
    //
    val weatherPrefs by viewModel.weatherPrefs.collectAsState()
    val weather by viewModel.weather.collectAsState()
    val grouped by viewModel.demos.collectAsState(initial = mapOf())
    //
    viewModel.weatherPrefs(context)
    //
    Column {
        DemosTopBar(
            weather,
            onWeatherClick
        )
        DemosListView(grouped, onDemoClick)
    }

    val isOver15Minutes = weatherPrefs.lastUpdated - System.currentTimeMillis() >= 15 * 60 * 1000
    val onPermissionsGranted = when {
        weatherPrefs.location.isNullOrEmpty() -> {
            { viewModel.onRequestLocation(true) }
        }
        weather == null || isOver15Minutes -> {
            { viewModel.requestWeather(context ,weatherPrefs.location, weatherPrefs.poi) }
        }
        else -> {
            {}
        }
    }
    //
    PermissionsRequest(viewModel, onPermissionsGranted)
}

@Composable
fun DemosTopBar(
    weather: Weather?,
    onWeatherClick: () -> Unit
) {
    Logger.d("$weather")
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.title_home))
        },
        modifier = Modifier.topAppBar(),
        actions = {
            weather?.let {
                WeatherActionItem(it, onWeatherClick)
            }
        }
    )
}

@Composable
private fun WeatherActionItem(
    weather: Weather,
    onWeatherClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable {
            onWeatherClick()
        },
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = weather.address,
            modifier = Modifier
                .padding(end = 8.dp),
        )
        Text(
            text = stringResource(
                id = R.string.weather_daily_temp,
                weather.weatherNow.text,
                weather.weatherNow.temp
            ),
            modifier = Modifier
                .padding(end = 8.dp),
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DemosListView(
    grouped: Map<Int, List<DemoItem>>,
    onDemoClick: (Int) -> Unit
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        grouped.forEach { (group, demos) ->
            stickyHeader {
                SectionView(group)
            }

            items(count = demos.size,
                key = { demos[it].item }) {
                DemoItemView(demos[it], onDemoClick)
            }
        }
    }
}

@Composable
fun DemoItemView(
    demoItem: DemoItem,
    onDemoClick: (Int) -> Unit
) {
    Text(
        text = stringResource(id = demoItem.item),
        modifier = Modifier
            .clickable {
                onDemoClick(demoItem.item)
            }
            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 16.dp)
            .fillMaxWidth(),
        style = DemoTheme.typography.h6,
        overflow = TextOverflow.Ellipsis,
        maxLines = 2,
    )
}
