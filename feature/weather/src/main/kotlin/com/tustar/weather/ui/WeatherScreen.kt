package com.tustar.weather.ui

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.tustar.data.Weather
import com.tustar.ui.ContentType
import com.tustar.ui.NavigationType
import com.tustar.utils.*
import com.tustar.weather.R

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherScreen(
    contentType: ContentType,
    navigationType: NavigationType,
    viewModel: WeatherViewModel = hiltViewModel(),
) {
    Logger.d("contentType:$contentType, navigationType=$navigationType")
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
    val context = LocalContext.current
    if (NetworkUtils.isNetworkConnected(context)) {
        val state = viewModel.state
        viewModel.getLocation(LocalContext.current)
        WeatherContent(state = state)
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(id = R.string.weather_no_network),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    context.actionWirelessSettings()
                }) {
                    Text(text = stringResource(id = R.string.weather_go_settings))
                }
            }
        }
    }
}

@Composable
fun WeatherContent(state: WeatherContact.State) {
    val listState = rememberLazyListState()
    Box {
        state.weather?.let { weather ->
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
                weatherBody(weather)
                item {
                    Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
                }
            }
        }

        if (state.loading) {
            LoadingBar()
        }
    }
}

private fun LazyListScope.weatherBody(
    weather: Weather
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
        ItemWeather24H(weather.hourly24H)
    }
    item {
        ItemWeather15D(weather.daily15D, weather.air5D)
    }
    item {
        ItemWeatherSunrise(weather.daily15D[0])
    }
    item {
        ItemWeatherIndices1D(weather.indices1D)
    }
    item {
        Text(
            text = stringResource(id = R.string.weather_sources),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFFF0F0ED),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        )
    }
}

@Composable
fun LoadingBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

