package com.tustar.weather.ui

import android.Manifest
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tustar.data.Weather
import com.tustar.ui.ContentType
import com.tustar.ui.NavigationType
import com.tustar.ui.design.theme.DemoTheme
import com.tustar.utils.*
import com.tustar.weather.R
import com.tustar.weather.WeatherPrefs

data class WeatherSize(
    val aspectRatio: Float = 1.0f, // 1.33
    val margin: Int = 8, // 24
    val titleMargin: Int = 12, //24
    val imgWidth: Int = 24, // 40
    val title1: TextStyle = TextStyle.Default
        .copy(color = Color.White), // MaterialTheme.typography.displayLarge
    val title2: TextStyle = TextStyle.Default
        .copy(color = Color.White), // MaterialTheme.typography.titleLarge
    val title3: TextStyle = TextStyle.Default
        .copy(color = Color.White), // MaterialTheme.typography.titleLarge
    val body1: TextStyle = TextStyle.Default
        .copy(color = Color.White), // MaterialTheme.typography.bodyLarge
    val body2: TextStyle = TextStyle.Default
        .copy(color = Color(0xFFF0F0ED)), // MaterialTheme.typography.bodyMedium
)

val LocalWeatherSize = compositionLocalOf { WeatherSize() }

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherScreen(
    contentType: ContentType,
    navigationType: NavigationType,
    viewModel: WeatherViewModel = hiltViewModel(),
) {
    CompositionLocalProvider(
        LocalWeatherSize provides WeatherSize(
            aspectRatio = 1.0f,
            margin = 8,
            titleMargin = 12,
            imgWidth = 24,
            title1 = MaterialTheme.typography.displayLarge.copy(
                fontSize = 80.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Thin,
                color = Color.White,
            ),
            title2 = MaterialTheme.typography.titleLarge.copy(
                fontSize = 16.sp,
                color = Color.White,
            ),
            title3 = MaterialTheme.typography.titleLarge.copy(
                fontSize = 15.sp,
                color = Color.White,
            ),
            body1 = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 14.sp,
                color = Color.White
            ),
            body2 = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 13.sp,
                color = Color(0xFFF0F0ED)
            ),
        )
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
            AllPermissionsGranted(viewModel.state, viewModel::getLocation) {
                viewModel.requestWeather(viewModel.state.weather.city)
            }
        } else {
            RequestLocations(locationPermissionsState)
        }
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
    state: WeatherContact.State,
    getLocation: (Context) -> Unit,
    refresh: () -> Unit,
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
            LocationEnable(state, getLocation, refresh)
        }
    }
}

@Composable
private fun LocationEnable(
    state: WeatherContact.State,
    getLocation: (Context) -> Unit,
    refresh: () -> Unit
) {
    val context = LocalContext.current
    if (NetworkUtils.isNetworkConnected(context)) {
        getLocation(context)
        WeatherContent(state = state, refresh = refresh)
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
fun WeatherContent(state: WeatherContact.State, refresh: () -> Unit) {
    val listState = rememberLazyListState()
    val rising by remember {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.any {
                it.key == "indices1D"
            }
        }
    }

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.loading)
    Box {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { refresh() },
            indicator = { state, trigger ->
                SwipeRefreshIndicator(
                    // Pass the SwipeRefreshState + trigger through
                    state = state,
                    refreshTriggerDistance = trigger,
                    // Enable the scale animation
                    scale = true,
                    // Change the color and shape
                    backgroundColor = MaterialTheme.colorScheme.inversePrimary,
                    shape = MaterialTheme.shapes.small,
                )
            }) {
            swipeRefreshState.isRefreshing = state.loading
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
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
                weatherBody(state.weather, rising)
            }
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
        }
    }
}

private fun LazyListScope.weatherBody(
    weather: Weather,
    rising: Boolean
) {
    item(key = "header") {
        ItemWeatherHeader(
            weather.city,
            weather.weatherNow,
            weather.warning,
            weather.airNow
        )
    }

    item(key = "24H") {
        ItemWeather24H(weather.hourly24H)
    }
    item(key = "15D") {
        ItemWeather15D(weather.daily15D, weather.air5D)
    }
    item(key = "sunrise") {
        ItemWeatherSunrise(weather.daily15D[0], rising)
    }
    item(key = "indices1D") {
        ItemWeatherIndices1D(weather.indices1D)
    }
    item(key = "footer") {
        Text(
            text = stringResource(id = R.string.weather_sources),
            textAlign = TextAlign.Center,
            style = LocalWeatherSize.current.body2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AllPermissionsGrantedPreview() {
    DemoTheme {
        AllPermissionsGranted(state = WeatherContact.State(
            weather = WeatherContact.State.Empty,
            prefs = WeatherPrefs.getDefaultInstance(),
            loading = false,
        ), getLocation = {

        }, refresh = {

        })
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherScreenPreview() {
    DemoTheme {
        WeatherContent(
            state = WeatherContact.State(
                weather = WeatherContact.State.Empty,
                prefs = WeatherPrefs.getDefaultInstance(),
                loading = false,
            ),
            refresh = {}
        )
    }
}