package com.tustar.demo.ui.home

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.SystemUiController
import com.tustar.data.DemoItem
import com.tustar.demo.R
import com.tustar.demo.ui.MainViewModel
import com.tustar.demo.ui.SectionView
import com.tustar.demo.ui.theme.DemoTheme
import com.tustar.demo.ui.topAppBar
import com.tustar.demo.util.Logger
import com.tustar.ktx.compose.selector
import com.tustar.weather.ui.HomeWeather
import com.tustar.weather.util.isNotValid

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
    val homeWeather by viewModel.homeWeather.collectAsState()
    val grouped by viewModel.demos.collectAsState(initial = mapOf())
    //
    viewModel.weatherPrefs(context)
    //
    Column {
        TopBar(
            homeWeather,
            onWeatherClick
        )
        ListView(grouped, onDemoClick)
    }

    val onPermissionsGranted = when {
        weatherPrefs.locate.isNotValid() -> {
            { viewModel.onRequestLocation(true) }
        }
        homeWeather == null -> {
            {
                viewModel.requestLocateWeather(
                    weatherPrefs.locate
                )
            }
        }
        else -> {
            {}
        }
    }
    PermissionsRequest(viewModel, onPermissionsGranted)
}

@Composable
private fun TopBar(
    weather: HomeWeather?,
    onWeatherClick: () -> Unit,
) {
    Logger.d("$weather")
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.title_home))
        },
        modifier = Modifier.topAppBar(),
        actions = {
            weather?.let {
                WeatherAction(it, onWeatherClick)
            }
        }
    )
}

@Composable
private fun WeatherAction(
    weather: HomeWeather,
    onWeatherClick: () -> Unit,
) {
    val (interactionSource, color) = selector(Color.White, Color(0x66FFFFFF))
    Column(
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) {
            onWeatherClick()
        },
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = weather.locate.name,
            color = color,
            modifier = Modifier
                .padding(end = 8.dp),
        )
        Text(
            text = stringResource(
                id = R.string.weather_daily_temp,
                weather.text,
                weather.temp
            ),
            color = color,
            modifier = Modifier
                .padding(end = 8.dp),
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ListView(
    grouped: Map<Int, List<DemoItem>>,
    onDemoClick: (Int) -> Unit,
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        grouped.forEach { (group, demos) ->
            stickyHeader {
                SectionView(group)
            }

            items(count = demos.size,
                key = { demos[it].item }) {
                ItemView(demos[it], onDemoClick)
            }
        }
    }
}

@Composable
private fun ItemView(
    demoItem: DemoItem,
    onDemoClick: (Int) -> Unit,
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
