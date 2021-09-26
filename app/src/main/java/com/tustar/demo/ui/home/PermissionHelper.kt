package com.tustar.demo.ui.home

import android.Manifest
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.tustar.demo.R
import com.tustar.ktx.actionApplicationDetailsSettings
import com.tustar.ktx.actionLocationSourceSettings
import com.tustar.ktx.isLocationEnable
import com.tustar.demo.ui.MainViewModel
import com.tustar.demo.ui.compose.ActionDialog
import com.tustar.demo.util.Logger

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsRequest(
    viewModel: MainViewModel,
    onPermissionsGranted: () -> Unit = {},
) {

    val doNotShow by viewModel.doNotShow.collectAsState()
    val onDoNotShow = viewModel::onDoNotShow

    val context = LocalContext.current
    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.RECORD_AUDIO
        )
    )

    when {
        // If all permissions are granted, then show the question
        multiplePermissionsState.allPermissionsGranted -> {
            Logger.d("allPermissionsGranted")
            // Location Service disable
            if (!context.isLocationEnable()) {
                LocationDisable()
            }
            //
            else {
                LaunchedEffect(true) {
                    onPermissionsGranted()
                }
            }
        }
        multiplePermissionsState.shouldShowRationale
                || !multiplePermissionsState.permissionRequested -> {
            Logger.d("shouldShowRationale || !permissionRequested")
            if (doNotShow) {
                // Feature not available
                Logger.w("Feature not available")
                PermissionsDenied(revokedPermissions = multiplePermissionsState.revokedPermissions)
            } else {
                Rationale(
                    revokedPermissions = multiplePermissionsState.revokedPermissions,
                    onRequestPermissions = { multiplePermissionsState.launchMultiplePermissionRequest() },
                    onDoNotShowRationale = onDoNotShow
                )
            }
        }
        // If the criteria above hasn't been met, the user denied some permission.
        else -> {
            Logger.d("permissionsDenied")
            PermissionsDenied(revokedPermissions = multiplePermissionsState.revokedPermissions)
        }
    }
}

@Composable
private fun LocationDisable() {
    var openDialog by rememberSaveable { mutableStateOf(true) }
    if (!openDialog) {
        return
    }

    val context = LocalContext.current
    ActionDialog(
        title = R.string.dlg_title_open_gps,
        cancelAction = {
            openDialog = false
        },
        confirmAction = {
            openDialog = false
            context.actionLocationSourceSettings()
        }) {
        Image(
            painter = painterResource(
                id = R.drawable.ic_dlg_gps
            ),
            contentDescription = null,
            modifier = Modifier
                .width(240.dp)
                .padding(bottom = 30.dp),
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun PermissionsDenied(
    revokedPermissions: List<PermissionState>,
) {
    var openDialog by rememberSaveable { mutableStateOf(true) }
    if (!openDialog) {
        return
    }

    val context = LocalContext.current
    val content = stringResource(
        id = R.string.dlg_content_no_permission,
        permissionsText(context, revokedPermissions)
    )
    ActionDialog(
        title = R.string.dlg_title_permission_manager,
        cancelAction = {
            openDialog = false
        },
        confirmAction = {
            openDialog = false
            context.actionApplicationDetailsSettings()
        }) {

        Text(
            text = content,
            color = Color(0xFF191919),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 30.dp),
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
private fun permissionsText(
    context: Context,
    permissions: List<PermissionState>
): String {
    return permissions.map {
        when (it.permission) {
            Manifest.permission.ACCESS_FINE_LOCATION -> {
                context.getString(R.string.permission_location)
            }
            Manifest.permission.ACCESS_COARSE_LOCATION -> {
                context.getString(R.string.permission_location)
            }
            Manifest.permission.RECORD_AUDIO -> {
                context.getString(R.string.permission_record_audio)
            }
            else -> ""
        }
    }.toSet().joinToString("、")
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun Rationale(
    revokedPermissions: List<PermissionState>,
    onRequestPermissions: () -> Unit,
    onDoNotShowRationale: (Context, Boolean) -> Unit,
) {
    val context = LocalContext.current
    Logger.d("Request permissions " + permissionsText(context, revokedPermissions))

    var checked by rememberSaveable { mutableStateOf(false) }
    val permissions = listOf(
        PermissionInfo(
            R.drawable.ic_permission_location,
            R.string.permission_location,
            R.string.permission_location_desc,
        ),
        PermissionInfo(
            R.drawable.ic_permission_record_audio,
            R.string.permission_record_audio,
            R.string.permission_record_audio_desc,
        )
    )
    ActionDialog(
        title = R.string.dlg_title_permission,
        cancelAction = {
            onDoNotShowRationale(context, checked)
        },
        confirmAction = {
            onDoNotShowRationale(context, checked)
            onRequestPermissions()
        }) {
        // content
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(items = permissions) {
                ItemPermission(info = it)
            }
        }
        //
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = {
                    checked = it
                },
                colors = CheckboxDefaults.colors(Color(0xFF596DFF)),
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            Text(
                text = stringResource(id = R.string.dlg_not_show),
                fontSize = 13.sp,
                color = Color(0xFF666666),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ItemPermission(info: PermissionInfo) {
    Row(
        modifier = Modifier
            .fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = info.icon), contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .width(24.dp)
        )
        Column(
        ) {
            Text(
                text = stringResource(id = info.title),
                color = Color(0xFF191919),
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier,
            )
            Text(
                text = stringResource(id = info.desc),
                color = Color(0xFF666666),
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 2.dp),
            )
        }
    }
}

data class PermissionInfo(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    @StringRes val desc: Int
)