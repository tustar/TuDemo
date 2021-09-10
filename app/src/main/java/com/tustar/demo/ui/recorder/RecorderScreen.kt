package com.tustar.demo.ui.recorder

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.tustar.annotation.DemoItem
import com.tustar.demo.R
import com.tustar.demo.ui.DetailTopBar
import com.tustar.demo.ui.MainViewModel
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
    Logger.d("maxAmplitude = ${info.maxAmplitude}")

    Column {
        DetailTopBar()
        WaveView(
            modifier = Modifier
                .height(500.dp)
                .fillMaxWidth(),
            info.maxAmplitude.coerceAtLeast(4)
        )

        RecorderButtons(info)
    }
}

@Composable
private fun RecorderButtons(
    info: RecorderInfo = RecorderInfo(),
) {
    val context = LocalContext.current
    var startAction = { RecorderService.startRecording(context) }
    val pauseAction = { RecorderService.pauseRecording(context) }
    val resumeAction = { RecorderService.resumeRecording(context) }
    val stopAction = { RecorderService.stopRecording(context) }
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