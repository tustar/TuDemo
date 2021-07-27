package com.tustar.demo.ui.home

import android.Manifest
import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.tustar.demo.R
import com.tustar.demo.ktx.actionApplicationDetailsSettings
import com.tustar.demo.ktx.actionLocationSourceSettings
import com.tustar.demo.ktx.isLocationEnable
import com.tustar.demo.ui.AppOpsResult
import com.tustar.demo.ui.LocalMainViewModel
import com.tustar.demo.ui.ShowPermissionDialog

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionsRequest(
    updateLocation: () -> Unit
) {
    val context = LocalContext.current
    // When true, the permissions request must be presented to the user.
    var launchPermissionsRequest by rememberSaveable { mutableStateOf(false) }
    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
    )
    when {
        // If all permissions are granted, then show the question
        multiplePermissionsState.allPermissionsGranted -> {
            // Location Service disable
            if (!context.isLocationEnable()) {
                LocationDisable()
            }
            //
            else {
                LaunchedEffect(true) {
                    updateLocation()
                }
            }
        }
        // The user denied some permissions but a rationale should be shown
        multiplePermissionsState.shouldShowRationale -> {
            launchPermissionsRequest = true
        }
        // The permissions are not granted, the rationale shouldn't be shown to the user,
        // and the permissions haven't been requested previously. Request permission!
        !multiplePermissionsState.permissionRequested -> {
            launchPermissionsRequest = true
        }
        // If the criteria above hasn't been met, the user denied some permission.
        else -> {
            PermissionsDenied()
        }
    }

    // Trigger a side-effect to request the permissions if they need to be presented to the user
    if (launchPermissionsRequest) {
        LaunchedEffect(multiplePermissionsState) {
            multiplePermissionsState.launchMultiplePermissionRequest()
            launchPermissionsRequest = false
        }
    }
}

@Composable
private fun LocationDisable() {
    val context = LocalContext.current
    val viewModel = LocalMainViewModel.current
    viewModel.liveResult.value = AppOpsResult(
        true,
        R.string.dlg_title_location_enable
    ) { context.actionLocationSourceSettings() }
    ShowPermissionDialog()
}

@Composable
private fun PermissionsDenied() {
    val context = LocalContext.current
    val viewModel = LocalMainViewModel.current
    viewModel.liveResult.value = AppOpsResult(
        true,
        R.string.dlg_title_location_permissons
    ) { context.actionApplicationDetailsSettings() }
    ShowPermissionDialog()
}