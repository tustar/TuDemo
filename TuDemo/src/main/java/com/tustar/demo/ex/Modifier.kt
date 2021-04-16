package com.tustar.demo.ex

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun Modifier.topAppBar() = this.then(
    background(MaterialTheme.colors.primary)
        .statusBarsPadding()
        .height(52.dp))