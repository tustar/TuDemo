package com.tustar.demo.ui.home

import android.Manifest
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.tustar.demo.R
import com.tustar.demo.ktx.actionApplicationDetailsSettings
import com.tustar.demo.ktx.actionLocationSourceSettings
import com.tustar.demo.ktx.isLocationEnable
import com.tustar.demo.ui.AppOpsResult
import com.tustar.demo.ui.PermissionDialogContent
import com.tustar.demo.ui.StateEvent
import com.tustar.demo.util.Logger

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionsRequest(
    onUpdateLocation: (Boolean) -> Unit,
    opsStateEvent: StateEvent<AppOpsResult>,
) {
    val context = LocalContext.current
    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
    )
    var doNotShowRationale by rememberSaveable { mutableStateOf(false) }

    when {
        // If all permissions are granted, then show the question
        multiplePermissionsState.allPermissionsGranted -> {
            // Location Service disable
            if (!context.isLocationEnable()) {
                LocationDisable(opsStateEvent)
            }
            //
            else {
                LaunchedEffect(true) {
                    onUpdateLocation(true)
                }
            }
        }
        multiplePermissionsState.shouldShowRationale ||
                !multiplePermissionsState.permissionRequested -> {
            if (doNotShowRationale) {
                // Feature not available
                Logger.w("Feature not available")
            } else {
                Rationale(
                    revokedPermissions = multiplePermissionsState.revokedPermissions,
                    onRequestPermissions = { multiplePermissionsState.launchMultiplePermissionRequest() },
                    onDoNotShowRationale = { doNotShowRationale = true },
                    opsStateEvent = opsStateEvent,
                )
            }
        }
        // If the criteria above hasn't been met, the user denied some permission.
        else -> {
            PermissionsDenied(
                revokedPermissions = multiplePermissionsState.revokedPermissions,
                opsStateEvent = opsStateEvent,
            )
        }
    }
}

@Composable
private fun LocationDisable(opsStateEvent: StateEvent<AppOpsResult>) {
    val context = LocalContext.current
    val opsResult = AppOpsResult(
        AppOpsResult.OPS_TAG_LOCATION,
        true,
        R.string.dlg_title_location_enable
    ) { context.actionLocationSourceSettings() }

    opsStateEvent.onEvent(opsResult)
    PermissionDialogContent(opsStateEvent)
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun Rationale(
    revokedPermissions: List<PermissionState>,
    onRequestPermissions: () -> Unit,
    onDoNotShowRationale: () -> Unit,
    opsStateEvent: StateEvent<AppOpsResult>,
) {
    val revokedPermissionsText = getPermissionsText(revokedPermissions)
    Logger.d(
        "$revokedPermissionsText important. " +
                "Please grant all of them for the app to function properly."
    )
    // Request permission
    Logger.d("Request permission")
//    PermissionsDenied(revokedPermissions = revokedPermissions, opsStateEvent = opsStateEvent)
    var ask by rememberSaveable { mutableStateOf(true) }
    LaunchedEffect(ask) {
        onRequestPermissions()
        ask = false
    }
    // Don't show rationale again
//    onDoNotShowRationale()
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun PermissionsDenied(
    revokedPermissions: List<PermissionState>,
    opsStateEvent: StateEvent<AppOpsResult>,
) {
    val revokedPermissionsText = getPermissionsText(revokedPermissions)
    Logger.d(
        "$revokedPermissionsText denied. See this FAQ with " +
                "information about why we need this permission. Please, grant us " +
                "access on the Settings screen."
    )
    Logger.d("opsResult = ${opsStateEvent.state}")
    val context = LocalContext.current
    val opsResult = AppOpsResult(
        AppOpsResult.OPS_TAG_LOCATION,
        true,
        R.string.dlg_title_location_permissons
    ) { context.actionApplicationDetailsSettings() }
    opsStateEvent.onEvent(opsResult)
    PermissionDialogContent(opsStateEvent)
}

@OptIn(ExperimentalPermissionsApi::class)
private fun getPermissionsText(permissions: List<PermissionState>): String {
    val revokedPermissionsSize = permissions.size
    if (revokedPermissionsSize == 0) return ""

    val textToShow = StringBuilder().apply {
        append("The ")
    }

    for (i in permissions.indices) {
        textToShow.append(permissions[i].permission)
        when {
            revokedPermissionsSize > 1 && i == revokedPermissionsSize - 2 -> {
                textToShow.append(", and ")
            }
            i == revokedPermissionsSize - 1 -> {
                textToShow.append(" ")
            }
            else -> {
                textToShow.append(", ")
            }
        }
    }
    textToShow.append(if (revokedPermissionsSize == 1) "permission is" else "permissions are")
    return textToShow.toString()
}