package com.tustar.demo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tustar.demo.R
import com.tustar.demo.ktx.topAppBar
import com.tustar.demo.ui.theme.DemoTheme
import com.tustar.demo.util.Logger

@Composable
fun DetailTopBar(demoItem: Int, upPress: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = stringResource(demoItem))
        },
        modifier = Modifier.topAppBar(),
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
fun ShowPermissionDialog(viewModel: MainViewModel) {
    val result by viewModel.liveResult.observeAsState(
        initial = AppOpsResult()
    )
    Logger.d("$result")
    PermissionDialogContent(result = result) {
        viewModel.liveResult.value = AppOpsResult()
    }
}

@Composable
fun PermissionDialogContent(result: AppOpsResult, dismissAction: () -> Unit) {
    if (!result.visible) {
        return
    }
    val title = result.title
    val confirmAction = {
        result.nextAction()
        dismissAction()
    }

    AlertDialog(
        modifier = Modifier.padding(20.dp),
        onDismissRequest = { },
        title = {
            Text(
                text = stringResource(id = title),
                style = DemoTheme.typography.h6,
                fontSize = 18.sp,
            )
        },
        confirmButton = {
            Text(
                text = stringResource(id = R.string.dlg_to_setting),
                style = DemoTheme.typography.button,
                color = DemoTheme.colors.primary,
                fontSize = 14.sp,
                modifier = Modifier
                    .clickable {
                        confirmAction()
                    }
                    .padding(12.dp)
            )
        },
        dismissButton = {
            Text(
                text = stringResource(id = R.string.dlg_not_now),
                style = DemoTheme.typography.button,
                fontSize = 14.sp,
                modifier = Modifier
                    .clickable {
                        dismissAction()
                    }
                    .padding(12.dp)
            )
        }
    )
}

@Composable
fun SectionView(resId: Int) {
    Text(
        text = stringResource(id = resId),
        modifier = Modifier
            .background(DemoTheme.demoColors.sectionBgColor)
            .padding(start = 16.dp, top = 2.dp, bottom = 2.dp)
            .fillMaxWidth(),
        style = DemoTheme.typography.subtitle2,
        color = DemoTheme.demoColors.sectionTextColor,
    )
}