package com.tustar.demo.ui.home

import android.Manifest
import android.app.AppOpsManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.tustar.demo.R
import com.tustar.demo.data.DemoItem
import com.tustar.demo.ktx.*
import com.tustar.demo.ui.AppOpsResult
import com.tustar.demo.ui.MainViewModel
import com.tustar.demo.ui.SectionView
import com.tustar.demo.ui.ShowPermissionDialog
import com.tustar.demo.ui.theme.DemoTheme
import com.tustar.demo.util.Logger

@Composable
fun DemosScreen(
    viewModel: MainViewModel,
    updateLocation: () -> Unit,
    onDemoClick: (Int) -> Unit,
) {
    val grouped by viewModel.createDemos().collectAsState(initial = mapOf())

    Column {
        DemosTopBar(viewModel, updateLocation)
        DemosListView(grouped, onDemoClick)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DemosTopBar(
    viewModel: MainViewModel,
    updateLocation: () -> Unit,
) {
    val context = LocalContext.current
    val weather by viewModel.liveWeather.observeAsState()
    //
    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
    )
    Logger.d("$weather")
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.title_home))
        },
        modifier = Modifier.topAppBar(),
        actions = {
            Logger.d(
                "shouldShowRationale = ${multiplePermissionsState.shouldShowRationale}, " +
                        "permissionRequested = ${multiplePermissionsState.permissionRequested}"
            )
            when {
                // If all permissions are granted, then show screen with the feature enabled
                multiplePermissionsState.allPermissionsGranted -> {
                    // Location Service disable
                    if (!context.isLocationEnable()) {
                        viewModel.liveResult.value = AppOpsResult(
                            true,
                            R.string.dlg_title_location_enable
                        ) { context.actionLocationSourceSettings() }
                        ShowPermissionDialog(viewModel = viewModel)
                    }
                    //
                    else {
                        SideEffect {
                            updateLocation()
                        }
                    }
                }
                // If the user denied any permission but a rationale should be shown, or the user sees
                // the permissions for the first time, explain why the feature is needed by the app and
                // allow the user decide if they don't want to see the rationale any more.
                multiplePermissionsState.shouldShowRationale || !multiplePermissionsState.permissionRequested -> {
                    SideEffect {
                        multiplePermissionsState.launchMultiplePermissionRequest()
                    }
                }
                //
                context.containsIgnoreOpstrs(
                    arrayOf(AppOpsManager.OPSTR_FINE_LOCATION, AppOpsManager.OPSTR_COARSE_LOCATION)
                ) -> {
                    viewModel.liveResult.value = AppOpsResult(
                        true,
                        R.string.dlg_title_location_permissons
                    ) { context.actionApplicationDetailsSettings() }
                    ShowPermissionDialog(viewModel = viewModel)
                }
            }
            weather?.let {
                Column(
                    modifier = Modifier.clickable {
                        updateLocation()
                    },
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = it.address,
                        modifier = Modifier
                            .padding(end = 8.dp),
                    )
                    Text(
                        text = stringResource(id = R.string.weather_daily_temp, it.daily, it.temp),
                        modifier = Modifier
                            .padding(end = 8.dp),
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DemosListView(
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
                DemoItemView(demos[it], onDemoClick)
            }
        }
    }
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
        style = DemoTheme.typography.h6,
        overflow = TextOverflow.Ellipsis,
        maxLines = 2,
    )
}
