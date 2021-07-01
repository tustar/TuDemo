package com.tustar.demo.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tustar.demo.ui.theme.DemoTheme

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    DemoTheme {
        NavGraph()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    DemoTheme(darkTheme = true) {
        NavGraph()
    }
}