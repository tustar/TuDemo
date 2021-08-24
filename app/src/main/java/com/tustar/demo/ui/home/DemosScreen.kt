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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.SystemUiController
import com.tustar.demo.R
import com.tustar.demo.data.DemoItem
import com.tustar.demo.data.Weather
import com.tustar.demo.ui.StateEvent
import com.tustar.demo.ktx.topAppBar
import com.tustar.demo.ui.AppOpsResult
import com.tustar.demo.ui.MainViewModel
import com.tustar.demo.ui.SectionView
import com.tustar.demo.ui.theme.DemoTheme
import com.tustar.demo.util.Logger

@Composable
fun DemosScreen(
    systemUiController: SystemUiController,
    viewModel: MainViewModel,
    onDemoClick: (Int) -> Unit,
    onWeatherClick: (Weather) -> Unit,
) {
    val statusBarColor = DemoTheme.colors.primarySurface
    SideEffect {
        systemUiController.setStatusBarColor(statusBarColor)
    }
    val weather by viewModel.weather.collectAsState()
    val onUpdateLocation = viewModel::onUpdateLocation
    val opsResult by viewModel.opsResult.collectAsState()
    val onOpsResultChange = viewModel::onOpsResultChange
    val opsStateEvent = StateEvent(opsResult, onOpsResultChange)
    val grouped by viewModel.demos.collectAsState(initial = mapOf())
    Column {
        DemosTopBar(
            weather, onUpdateLocation,
            opsStateEvent,
            onWeatherClick
        )
        DemosListView(grouped, onDemoClick)
    }
}

@Composable
fun DemosTopBar(
    weather: Weather?,
    onUpdateLocation: (Boolean) -> Unit,
    opsStateEvent: StateEvent<AppOpsResult>,
    onWeatherClick: (Weather) -> Unit
) {
    Logger.d("$weather")
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.title_home))
        },
        modifier = Modifier.topAppBar(),
        actions = {
            LocationPermissionsRequest(onUpdateLocation, opsStateEvent)
            weather?.let {
                WeatherActionItem(it, onUpdateLocation, onWeatherClick)
            }
        }
    )
}

@Composable
private fun WeatherActionItem(
    weather: Weather,
    onUpdateLocation: (Boolean) -> Unit,
    onWeatherClick: (Weather) -> Unit
) {
    Column(
        modifier = Modifier.clickable {
            onUpdateLocation(true)
            onWeatherClick(weather)
        },
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = weather.address,
            modifier = Modifier
                .padding(end = 8.dp),
        )
        Text(
            text = stringResource(id = R.string.weather_daily_temp, weather.daily, weather.temp),
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
