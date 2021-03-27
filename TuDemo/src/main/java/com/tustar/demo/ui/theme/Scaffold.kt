package com.tustar.demo.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable

@Composable
fun DemoScaffold(content: @Composable () -> Unit) {
    DemoTheme {
        Surface(color = MaterialTheme.colors.primary) {
            content()
        }
    }
}