/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tustar.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

// Dark
private val DarkColorPalette = darkColors(
    primary = blue900,
    primaryVariant = blue800,
    secondary = teal200,
    background = Color(0xFF2B2B2B),
    surface = Color(0xFF2B2B2B),
)
private val DarkDemoColorPalette = darkDemoColors(
    sectionTextColor = Color(0xFF5084B8),
    sectionBgColor = Color(0xFF323232)
)

// Light
private val LightColorPalette = lightColors(
    primary = blue600,
    primaryVariant = blue700,
    secondary = blueA100,
    background = Color(0xFFFAFAFA),
    surface = Color(0xFFFAFAFA),
)
private val LightDemoColorPalette = lightDemoColors(
    sectionTextColor = Color(0xFF03A9F4),
    sectionBgColor = Color(0xFFF3F6FA)
)


@Composable
fun DemoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
    val demoColors = if (darkTheme) DarkDemoColorPalette else LightDemoColorPalette

    ProvideDemoColors(demoColors) {
        MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = shapes,
            content = content,
        )
    }
}

object DemoTheme {
    val demoColors: DemoColors
        @Composable
        get() = LocalDemoColors.current

    /**
     * Proxy to [MaterialTheme]
     */
    val colors: Colors
        @Composable
        get() = MaterialTheme.colors

    /**
     * Proxy to [MaterialTheme]
     */
    val typography: Typography
        @Composable
        get() = MaterialTheme.typography

    /**
     * Proxy to [MaterialTheme]
     */
    val shapes: Shapes
        @Composable
        get() = MaterialTheme.shapes
}

@Composable
fun ProvideDemoColors(
    colors: DemoColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember {
        // Explicitly creating a new object here so we don't mutate the initial [colors]
        // provided, and overwrite the values set in it.
        colors.copy()
    }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalDemoColors provides colorPalette, content = content)
}
