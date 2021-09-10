package com.tustar.demo.ui.home

import android.Manifest
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.tustar.demo.R
import com.tustar.demo.ktx.isLocationEnable
import com.tustar.demo.util.Logger

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsRequest(
    onUpdateLocation: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.RECORD_AUDIO
        )
    )
    var doNotShowRationale by rememberSaveable { mutableStateOf(false) }

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
                    onUpdateLocation(true)
                }
            }
        }
        multiplePermissionsState.shouldShowRationale ||
                !multiplePermissionsState.permissionRequested -> {
            if (doNotShowRationale) {
                // Feature not available
                Logger.w("Feature not available")
                Toast.makeText(context, R.string.dlg_permission_tips, Toast.LENGTH_SHORT)
                    .show()
            } else {
                Rationale(
                    revokedPermissions = multiplePermissionsState.revokedPermissions,
                    onRequestPermissions = { multiplePermissionsState.launchMultiplePermissionRequest() },
                    onDoNotShowRationale = { doNotShowRationale = it }
                )
            }
        }
        // If the criteria above hasn't been met, the user denied some permission.
        else -> {
            PermissionsDenied(revokedPermissions = multiplePermissionsState.revokedPermissions)
        }
    }
}

@Composable
private fun LocationDisable() {

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun PermissionsDenied(
    revokedPermissions: List<PermissionState>,
) {
    val revokedPermissionsText = getPermissionsText(revokedPermissions)
    Logger.d(
        "$revokedPermissionsText denied. See this FAQ with " +
                "information about why we need this permission. Please, grant us " +
                "access on the Settings screen."
    )

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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun Rationale(
    revokedPermissions: List<PermissionState>,
    onRequestPermissions: () -> Unit,
    onDoNotShowRationale: (Boolean) -> Unit,
) {
    val revokedPermissionsText = getPermissionsText(revokedPermissions)
    Logger.d(
        "$revokedPermissionsText important. " +
                "Please grant all of them for the app to function properly."
    )
    // Request permission
    Logger.d("Request permission")

    var openDialog by rememberSaveable { mutableStateOf(true) }
    if (!openDialog) {
        return
    }

    Dialog(onDismissRequest = { /*TODO*/ }) {

        Column(
            modifier = Modifier
                .width(300.dp)
                .height(320.dp)
                .background(Color.White, RoundedCornerShape(8.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // title
            Text(
                text = stringResource(id = R.string.dlg_permission_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 16.dp)
            )
            // content
            Text(
                text = stringResource(id = R.string.dlg_permission_content),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            )
            val permissions = listOf(
                PermissionInfo(
                    R.drawable.ic_permission_location,
                    R.string.dlg_content_location,
                    R.string.dlg_content_location_desc,
                ),
                PermissionInfo(
                    R.drawable.ic_permission_record_audio,
                    R.string.dlg_content_record_audio,
                    R.string.dlg_content_record_audio_desc,
                )
            )
            // content permissions
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp)
            ) {
                items(items = permissions) {
                    ItemPermission(info = it)
                }
            }
            Divider(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )
            // buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = stringResource(id = R.string.cancel),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                        ) {
                            openDialog = false
                        }
                )

                Divider(
                    Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                )
                Text(text = stringResource(id = R.string.ok),
                    textAlign = TextAlign.Center,
                    color = Color(0xFF579DFF),
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                        ) {
                            onRequestPermissions()
                        })
            }
        }
    }
}

@Composable
fun ItemPermission(info: PermissionInfo) {
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = info.icon), contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .width(30.dp)
        )
        Column(
        ) {
            Text(
                text = stringResource(id = info.title),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier,
            )
            Text(
                text = stringResource(id = info.desc),
                fontSize = 14.sp,
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