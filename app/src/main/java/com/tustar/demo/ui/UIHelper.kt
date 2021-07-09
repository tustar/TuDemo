package com.tustar.demo.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tustar.demo.R
import com.tustar.demo.ex.openSettings
import com.tustar.demo.ex.topAppBar
import com.tustar.demo.util.Logger

@Composable
fun DetailTopBar(demoItem: Int, upPress: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = stringResource(demoItem))
        },
        modifier = Modifier.topAppBar(),
        backgroundColor = MaterialTheme.colors.primary,
        navigationIcon = {
            IconButton(onClick = upPress) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(demoItem)
                )
            }
        }
    )
}

@Composable
fun showLocationDialog(viewModel: MainViewModel) {

    val context = LocalContext.current
    val resultState = viewModel.liveResult.observeAsState(
        initial = AppOpsResult()
    )
    Logger.d("${resultState.value}")
    if (!resultState.value.visible) {
        return
    }
    val title = if (resultState.value.rationale) R.string.dlg_title_location_permissons else
        R.string.dlg_title_location_enable

    AlertDialog(
        modifier = Modifier.padding(20.dp),
        onDismissRequest = { },
        title = {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.h6,
                fontSize = 18.sp
            )
        },
        confirmButton = {
            Text(
                text = stringResource(id = R.string.dlg_to_setting),
                style = MaterialTheme.typography.button,
                color = MaterialTheme.colors.primary,
                fontSize = 14.sp,
                modifier = Modifier
                    .clickable {
                        context.openSettings(resultState.value.rationale)
                        viewModel.liveResult.value = AppOpsResult()
                    }
                    .padding(12.dp)
            )
        },
        dismissButton = {
            Text(
                text = stringResource(id = R.string.dlg_not_now),
                style = MaterialTheme.typography.button,
//                                color = MaterialTheme.colors.primary,
                fontSize = 14.sp,
                modifier = Modifier
                    .clickable {
                        viewModel.liveResult.value = AppOpsResult()
                    }
                    .padding(12.dp)
            )
        }
    )
}