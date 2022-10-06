/*
 * Copyright 2022 The Android Open Source Project
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

package com.tustar.ui.design.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.tustar.ui.design.shape.shapes

// Material 3 color schemes
private val demoDarkColorScheme = darkColorScheme(
    primary = demoDarkPrimary,
    onPrimary = demoDarkOnPrimary,
    primaryContainer = demoDarkPrimaryContainer,
    onPrimaryContainer = demoDarkOnPrimaryContainer,
    inversePrimary = demoDarkPrimaryInverse,
    secondary = demoDarkSecondary,
    onSecondary = demoDarkOnSecondary,
    secondaryContainer = demoDarkSecondaryContainer,
    onSecondaryContainer = demoDarkOnSecondaryContainer,
    tertiary = demoDarkTertiary,
    onTertiary = demoDarkOnTertiary,
    tertiaryContainer = demoDarkTertiaryContainer,
    onTertiaryContainer = demoDarkOnTertiaryContainer,
    error = demoDarkError,
    onError = demoDarkOnError,
    errorContainer = demoDarkErrorContainer,
    onErrorContainer = demoDarkOnErrorContainer,
    background = demoDarkBackground,
    onBackground = demoDarkOnBackground,
    surface = demoDarkSurface,
    onSurface = demoDarkOnSurface,
    inverseSurface = demoDarkInverseSurface,
    inverseOnSurface = demoDarkInverseOnSurface,
    surfaceVariant = demoDarkSurfaceVariant,
    onSurfaceVariant = demoDarkOnSurfaceVariant,
    outline = demoDarkOutline
)

private val demoLightColorScheme = lightColorScheme(
    primary = demoLightPrimary,
    onPrimary = demoLightOnPrimary,
    primaryContainer = demoLightPrimaryContainer,
    onPrimaryContainer = demoLightOnPrimaryContainer,
    inversePrimary = demoLightPrimaryInverse,
    secondary = demoLightSecondary,
    onSecondary = demoLightOnSecondary,
    secondaryContainer = demoLightSecondaryContainer,
    onSecondaryContainer = demoLightOnSecondaryContainer,
    tertiary = demoLightTertiary,
    onTertiary = demoLightOnTertiary,
    tertiaryContainer = demoLightTertiaryContainer,
    onTertiaryContainer = demoLightOnTertiaryContainer,
    error = demoLightError,
    onError = demoLightOnError,
    errorContainer = demoLightErrorContainer,
    onErrorContainer = demoLightOnErrorContainer,
    background = demoLightBackground,
    onBackground = demoLightOnBackground,
    surface = demoLightSurface,
    onSurface = demoLightOnSurface,
    inverseSurface = demoLightInverseSurface,
    inverseOnSurface = demoLightInverseOnSurface,
    surfaceVariant = demoLightSurfaceVariant,
    onSurfaceVariant = demoLightOnSurfaceVariant,
    outline = demoLightOutline
)

@Composable
fun DemoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val demoColorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> demoDarkColorScheme
        else -> demoLightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = demoColorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = demoColorScheme,
        typography = demoTypography,
        shapes = shapes,
        content = content
    )
}
