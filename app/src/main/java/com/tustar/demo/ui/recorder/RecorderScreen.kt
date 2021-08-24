package com.tustar.demo.ui.recorder

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.tustar.annotation.DemoItem
import com.tustar.demo.R
import com.tustar.demo.ui.StateEvent
import com.tustar.demo.ktx.actionApplicationDetailsSettings
import com.tustar.demo.ui.AppOpsResult
import com.tustar.demo.ui.DetailTopBar
import com.tustar.demo.ui.MainViewModel
import com.tustar.demo.ui.PermissionDialogContent
import com.tustar.demo.ui.theme.DemoTheme
import com.tustar.demo.util.Logger
import kotlinx.coroutines.DelicateCoroutinesApi

const val MIN_OFFSET_Y = 6.0F

@OptIn(ExperimentalPermissionsApi::class)
@DelicateCoroutinesApi
@DemoItem(
    group = R.string.group_custom_widget,
    item = R.string.custom_wave_view,
    createdAt = "2021-07-01 14:49:50",
    updatedAt = "2021-07-01 14:49:50",
)
@Composable
fun RecorderScreen(viewModel: MainViewModel) {
    val info by viewModel.recorderInfo.collectAsState()
    val opsResult by viewModel.opsResult.collectAsState()
    val onOpsResultChange = viewModel::onOpsResultChange
    val opsStateEvent = StateEvent(opsResult, onOpsResultChange)
    Logger.d("maxAmplitude = ${info.maxAmplitude}")
    val context = LocalContext.current
    var startAction = { RecorderService.startRecording(context) }
    val pauseAction = { RecorderService.pauseRecording(context) }
    val resumeAction = { RecorderService.resumeRecording(context) }
    val stopAction = { RecorderService.stopRecording(context) }

    Column {
        DetailTopBar()
        WaveView(
            modifier = Modifier
                .height(500.dp)
                .fillMaxWidth(),
            info.maxAmplitude.coerceAtLeast(4)
        )

        val permissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)
        var doNotShowRationale by rememberSaveable { mutableStateOf(false) }
        PermissionRequired(
            permissionState = permissionState,
            permissionNotGrantedContent = {
                if (doNotShowRationale) {
                    // Feature not available
                    Logger.w("Feature not available")
                } else {
                    Rationale(
                        onRequestPermission = { permissionState.launchPermissionRequest() },
                        onDoNotShowRationale = { doNotShowRationale = true },
                    )
                }

            },
            permissionNotAvailableContent = {
                RecorderButtons(startAction = { permissionState.launchPermissionRequest() })
                PermissionsDenied(opsStateEvent)
            }
        ) {
            RecorderButtons(info, startAction, pauseAction, resumeAction, stopAction)
        }
    }
}

@Composable
private fun Rationale(
    onRequestPermission: () -> Unit,
    onDoNotShowRationale: () -> Unit,
) {
    Logger.d("The audio is important for this app. Please grant the permission.")
    // Request permission
    RecorderButtons(startAction = onRequestPermission)
    // Don't show rationale again
//    onDoNotShowRationale()
}

@Composable
private fun PermissionsDenied(opsStateEvent: StateEvent<AppOpsResult>) {
    Logger.d(
        "Audio permission denied. See this FAQ with information about why we " +
                "need this permission. Please, grant us access on the Settings screen."
    )
    val context = LocalContext.current
    val opsResult = AppOpsResult(
        tag = AppOpsResult.OPS_TAG_AUDIO,
        true,
        R.string.dlg_title_audio_permisson
    ) { context.actionApplicationDetailsSettings() }
    opsStateEvent.onEvent(opsResult)
    PermissionDialogContent(opsStateEvent)
}

@Composable
private fun RecorderButtons(
    info: RecorderInfo = RecorderInfo(),
    startAction: () -> Unit,
    pauseAction: () -> Unit = {},
    resumeAction: () -> Unit = {},
    stopAction: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        var id = R.string.start
        var tmpAction = startAction
        when (info.state) {
            State.IDLE -> {
                id = R.string.start
                tmpAction = startAction
            }
            State.RECORDING, State.RECORDING_BY_RESUME -> {
                id = R.string.pause
                tmpAction = pauseAction
            }
            State.RECORDING_PAUSE -> {
                id = R.string.resume
                tmpAction = resumeAction
            }
        }
        Button(
            onClick = tmpAction, modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = id),
                style = DemoTheme.typography.button,
                fontSize = 20.sp,
            )
        }
        val enable = info.state in arrayOf(
            State.RECORDING,
            State.RECORDING_PAUSE,
            State.RECORDING_BY_RESUME
        )
        Button(
            onClick = stopAction, enabled = enable, modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.stop),
                style = DemoTheme.typography.button,
                fontSize = 20.sp,
            )
        }
    }
}