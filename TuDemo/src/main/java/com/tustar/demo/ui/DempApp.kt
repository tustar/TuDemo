package com.tustar.demo.ui

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.google.accompanist.insets.ProvideWindowInsets
import com.tustar.demo.ui.utils.LocalBackDispatcher

@Composable
fun DemoApp(backDispatcher: OnBackPressedDispatcher) {
    CompositionLocalProvider(LocalBackDispatcher provides backDispatcher) {
        ProvideWindowInsets {
            NavGraph()
        }
    }
}