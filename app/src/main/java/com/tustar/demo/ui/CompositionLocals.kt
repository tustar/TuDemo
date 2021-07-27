package com.tustar.demo.ui

import androidx.compose.runtime.compositionLocalOf
import com.tustar.demo.R

var LocalMainViewModel = compositionLocalOf { MainViewModel() }
val LocalBackPressedDispatcher = compositionLocalOf { {} }
val LocalDemoId = compositionLocalOf { R.string.custom_wave_view}