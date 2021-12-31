package com.tustar.weather.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.SystemUiController
import com.tustar.data.source.remote.City

@Composable
fun AddCityScreen(
    systemUiController: SystemUiController,
    viewModel: WeatherViewModel,
) {
    SideEffect {
        systemUiController.setStatusBarColor(color = Color.Transparent)
    }

    val topCities by viewModel.topCities.collectAsState()
    AddCityContent(topCities)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AddCityContent(topCities: List<City>) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight()
    ) {
        items(items = topCities) { city ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = city.name)
            }
        }
    }
}

