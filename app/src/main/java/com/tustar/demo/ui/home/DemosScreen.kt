package com.tustar.demo.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tustar.demo.R
import com.tustar.demo.data.model.DemoItem
import com.tustar.demo.ex.topAppBar
import com.tustar.demo.ui.MainViewModel
import com.tustar.demo.ui.theme.sectionBgColor
import com.tustar.demo.ui.theme.sectionTextColor
import com.tustar.demo.ui.theme.typography
import com.tustar.demo.ui.weather.Weather
import com.tustar.demo.util.Logger

@Composable
fun DemosScreen(
    viewModel: MainViewModel,
    updateLocation: () -> Unit,
    onDemoClick: (Int) -> Unit,
    modifier: Modifier
) {
    val weatherState = viewModel.now.observeAsState()
    val groupedState = viewModel.createDemos().collectAsState(initial = mapOf())

    Column(modifier) {
        DemosTopBar(weatherState, updateLocation)
        DemosContent(groupedState, onDemoClick)
    }
}

@Composable
fun DemosTopBar(
    weatherState: State<Weather?>,
    updateLocation: () -> Unit,
) {
    Logger.d("${weatherState.value}")
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.title_home))
        },
        modifier = Modifier.topAppBar(),
        backgroundColor = MaterialTheme.colors.primary,
        actions = {
            weatherState.value?.let {
                Column(
                    modifier = Modifier.clickable {
                        updateLocation()
                    },
                    horizontalAlignment = Alignment.End
                ) {
                    Text(it.address)
                    Text(stringResource(id = R.string.weather_daily_temp, it.daily, it.temp))
                }
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DemosContent(
    groupedState: State<Map<Int, List<DemoItem>>>,
    onDemoClick: (Int) -> Unit,
) {

    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        groupedState.value.forEach { (group, demos) ->
            stickyHeader {
                DemosHeader(group)
            }

            items(count = demos.size,
                key = {
                    demos[it].item
                }) {
                DemoItemView(demos[it], onDemoClick)
            }
        }
    }
}

@Composable
fun DemosHeader(group: Int) {
    Text(
        text = stringResource(id = group),
        modifier = Modifier
            .background(sectionBgColor)
            .padding(start = 16.dp, top = 2.dp, bottom = 2.dp)
            .fillMaxWidth(),
        style = typography.subtitle2,
        color = sectionTextColor,
    )
}

@Composable
fun DemoItemView(
    demoItem: DemoItem,
    onDemoClick: (Int) -> Unit,
) {
    Text(
        text = stringResource(id = demoItem.item),
        modifier = Modifier
            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 16.dp)
            .fillMaxWidth()
            .clickable {
                onDemoClick(demoItem.item)
            },
        style = typography.h6,
        overflow = TextOverflow.Ellipsis,
        maxLines = 2,
    )
}
