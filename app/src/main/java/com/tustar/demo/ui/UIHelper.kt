package com.tustar.demo.ui

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tustar.demo.ex.topAppBar

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