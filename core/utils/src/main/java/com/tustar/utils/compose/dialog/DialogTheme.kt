package com.tustar.utils.compose.dialog

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color


private val DarkDialogColorPalette = darkDialogColors(
    background = Color(0xFF222433),
    title = Color(0xFFE0E0F0),
    subTitle = Color(0x99E0E0F0),
    divider = Color(0x1FE0E0F0),
    buttonText = Color(0xFF596DFF)
)

private val LightDialogColorPalette = lightDialogColors(
    background = Color(0xFFFFFFFF),
    title = Color(0xFF191919),
    subTitle = Color(0xFF666666),
    divider = Color(0xFFE5E5E5),
    buttonText = Color(0xFF596DFF)
)

@Composable
fun DialogTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val dialogColors = if (darkTheme) DarkDialogColorPalette else LightDialogColorPalette

    ProvideDialogColors(dialogColors) {
        content()
    }
}

object DialogTheme {
    val colors: DialogColors
        @Composable
        get() = LocalDialogColors.current
}

@Composable
fun ProvideDialogColors(
    colors: DialogColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember {
        // Explicitly creating a new object here so we don't mutate the initial [colors]
        // provided, and overwrite the values set in it.
        colors.copy()
    }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalDialogColors provides colorPalette, content = content)
}