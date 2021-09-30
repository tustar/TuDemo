package com.tustar.demo.ui.compose.dialog

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

@Stable
class DialogColors(
    background: Color,
    title: Color,
    subTitle: Color,
    divider: Color,
    buttonText: Color,
    isLight: Boolean
) {
    var background by mutableStateOf(background)
        private set
    var title by mutableStateOf(title)
        private set
    var subTitle by mutableStateOf(subTitle)
        private set
    var divider by mutableStateOf(divider)
        private set
    var buttonText by mutableStateOf(buttonText)
        private set
    var isLight by mutableStateOf(isLight)
        private set

    fun update(other: DialogColors) {
        background = other.background
        title = other.title
        subTitle = other.subTitle
        divider = other.divider
        buttonText = other.buttonText
        isLight = other.isLight
    }

    fun copy(): DialogColors = DialogColors(
        background = background,
        title = title,
        subTitle = subTitle,
        divider = divider,
        buttonText = buttonText,
        isLight = isLight,
    )
}

fun darkDialogColors(
    background: Color,
    title: Color,
    subTitle: Color,
    divider: Color,
    buttonText: Color
) = DialogColors(background, title, subTitle, divider, buttonText, false)

fun lightDialogColors(
    background: Color,
    title: Color,
    subTitle: Color,
    divider: Color,
    buttonText: Color,
) = DialogColors(background, title, subTitle, divider, buttonText, true)

internal val LocalDialogColors = staticCompositionLocalOf<DialogColors> {
    error("No DemoColorPalette provided")
}
