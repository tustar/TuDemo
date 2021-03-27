package com.tustar.demo.ui.me

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tustar.demo.R

@Composable
fun MeContent(
    modifier: Modifier
) {
    Text(
        text = stringResource(id = R.string.title_me),
        modifier = modifier
    )
}