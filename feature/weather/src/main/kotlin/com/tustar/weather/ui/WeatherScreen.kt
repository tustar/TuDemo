package com.tustar.weather.ui

import android.Manifest
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.tustar.data.Weather
import com.tustar.utils.actionLocationSourceSettings
import com.tustar.utils.isLocationEnable
import com.tustar.utils.observeAsState
import com.tustar.weather.R
import com.tustar.weather.WeatherPrefs
import com.tustar.weather.util.TrendSwitchMode

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel(),
) {
    val permissions = mutableListOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//        permissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
//    }
    val locationPermissionsState = rememberMultiplePermissionsState(permissions)
    if (locationPermissionsState.allPermissionsGranted) {
        AllPermissionsGranted(viewModel)
    } else {
        RequestLocations(locationPermissionsState)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun RequestLocations(state: MultiplePermissionsState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val allPermissionsRevoked =
                state.permissions.size ==
                        state.revokedPermissions.size

            val textToShow = if (!allPermissionsRevoked) {
                // If not all the permissions are revoked, it's because the user accepted the COARSE
                // location permission, but not the FINE one.
                stringResource(id = R.string.weather_request_fine_location_content)
            } else if (state.shouldShowRationale) {
                // Both location permissions have been denied
                stringResource(id = R.string.weather_request_locations_content)
            } else {
                // First time the user sees this feature or the user doesn't want to be asked again
                stringResource(id = R.string.weather_request_locations_content)
            }

            val buttonText = if (!allPermissionsRevoked) {
                stringResource(id = R.string.weather_request_fine_location)
            } else {
                stringResource(id = R.string.weather_request_locations)
            }
            Text(
                text = textToShow,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                state.launchMultiplePermissionRequest()
            }) {
                Text(buttonText)
            }
        }
    }
}

@Composable
private fun AllPermissionsGranted(
    viewModel: WeatherViewModel
) {
    val context = LocalContext.current
    val lifecycleState by LocalLifecycleOwner.current.lifecycle.observeAsState()
    if (lifecycleState == Lifecycle.Event.ON_RESUME) {
        if (!context.isLocationEnable()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(id = R.string.weather_location_content),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        context.actionLocationSourceSettings()
                    }) {
                        Text(text = stringResource(id = R.string.weather_go_settings))
                    }
                }
            }
        } else {
            //
            LocationEnable(viewModel)
        }
    }
}

@Composable
private fun LocationEnable(
    viewModel: WeatherViewModel
) {
    viewModel.getLocation(LocalContext.current)
    WeatherContent(
        viewModel = viewModel
    )
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun WeatherContent(
    viewModel: WeatherViewModel
) {
    //
    val weatherUiState by viewModel.uiState.collectAsStateWithLifecycle()
    WeatherContent(
        weatherUiState = weatherUiState,
        saveMode24H = viewModel::saveMode24H,
        saveMode15D = viewModel::saveMode24H
    )
}

@Composable
private fun WeatherContent(
    weatherUiState: WeatherUiState,
    saveMode24H: (Context, WeatherPrefs.Mode) -> Unit,
    saveMode15D: (Context, WeatherPrefs.Mode) -> Unit,
) {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary,
                    )
                )
            )
    ) {
        item {
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
        }
        when (weatherUiState) {
            WeatherUiState.Loading -> item {

            }
            is WeatherUiState.Success -> {
                val weather = weatherUiState.weather
                val mode24H = TrendSwitchMode(weatherUiState.prefs.mode24H, saveMode24H)
                val mode15D = TrendSwitchMode(weatherUiState.prefs.mode24H, saveMode15D)
                WeatherBody(weather, mode24H, mode15D)
            }
            WeatherUiState.Error -> {

            }
        }
        item {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
        }
    }
}

private fun LazyListScope.WeatherBody(
    weather: Weather,
    mode24H: TrendSwitchMode,
    mode15D: TrendSwitchMode
) {
    item {
        ItemWeatherHeader(
            weather.city,
            weather.weatherNow,
            weather.warning,
            weather.airNow
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
}

