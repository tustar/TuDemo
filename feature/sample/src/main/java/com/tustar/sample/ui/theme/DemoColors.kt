package com.tustar.sample.ui.theme

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

@Stable
class DemoColors(
    sectionTextColor: Color,
    sectionBgColor: Color,
    isLight: Boolean
) {
    var sectionTextColor by mutableStateOf(sectionTextColor)
        private set
    var sectionBgColor by mutableStateOf(sectionBgColor)
        private set
    var isLight by mutableStateOf(isLight)
        private set

    fun update(other: DemoColors) {
        sectionTextColor = other.sectionTextColor
        sectionBgColor = other.sectionBgColor
        isLight = other.isLight
    }

    fun copy(): DemoColors = DemoColors(
        sectionTextColor = sectionTextColor,
        sectionBgColor = sectionBgColor,
        isLight = isLight,
    )
}

fun darkDemoColors(
    sectionTextColor: Color = Color.White,
    sectionBgColor: Color = Color.Black,
) = DemoColors(sectionTextColor, sectionBgColor, false)

fun lightDemoColors(
    sectionTextColor: Color = Color.Black,
    sectionBgColor: Color = Color.White,
) = DemoColors(sectionTextColor, sectionBgColor, true)

internal val LocalDemoColors = staticCompositionLocalOf<DemoColors> {
    error("No DemoColorPalette provided")
}
