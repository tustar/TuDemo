package com.tustar.demo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tustar.demo.ktx.topAppBar
import com.tustar.demo.ui.theme.DemoTheme

@Composable
fun DetailTopBar() {
    val demoItem = LocalDemoId.current
    TopAppBar(
        title = {
            Text(text = stringResource(demoItem))
        },
        modifier = Modifier.topAppBar(),
        navigationIcon = {
            IconButton(onClick = LocalBackPressedDispatcher.current) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(demoItem)
                )
            }
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