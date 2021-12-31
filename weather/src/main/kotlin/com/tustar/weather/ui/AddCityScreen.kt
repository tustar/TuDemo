package com.tustar.weather.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.insets.statusBarsHeight
import com.tustar.data.source.remote.City
import com.tustar.weather.Location
import com.tustar.weather.R
import com.tustar.weather.util.toLocation

@Composable
fun AddCityScreen(
    navController: NavHostController,
    viewModel: WeatherViewModel,
) {
    val topCities by viewModel.topCities.collectAsState()
    viewModel.requestTopCities()
    val context = LocalContext.current
    val onBack = {
        navController.navigate(WeatherDestinations.ROUTE_WEATHER) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val onAddCity = { location: Location -> viewModel.onAddCity(context, location) }
    AddCityContent(topCities, onBack, onAddCity)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AddCityContent(
    topCities: List<City>,
    onBackPressed: () -> Unit,
    onAddCity: (Location) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.statusBarsHeight())
        TopAppBar(
            title = {
                Text(text = stringResource(R.string.weather_add_city))
            },
            navigationIcon = {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = null,
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = stringResource(R.string.weather_top_cities),
            modifier = Modifier.padding(vertical = 12.dp))
        LazyVerticalGrid(
            cells = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 12.dp)
        ) {
            items(items = topCities) { city ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = city.name,
                        modifier = Modifier.padding(vertical = 12.dp)
                            .clickable {
                                onAddCity(city.toLocation())
                            })
                }
            }
        }
    }
}

