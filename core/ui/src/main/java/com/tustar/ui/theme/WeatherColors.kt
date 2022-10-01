package com.tustar.ui.theme

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

@Stable
class WeatherColors(
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

    fun update(other: WeatherColors) {
        sectionTextColor = other.sectionTextColor
        sectionBgColor = other.sectionBgColor
        isLight = other.isLight
    }

    fun copy(): WeatherColors = WeatherColors(
        sectionTextColor = sectionTextColor,
        sectionBgColor = sectionBgColor,
        isLight = isLight,
    )
}

fun darkWeatherColors(
    sectionTextColor: Color = Color.White,
    sectionBgColor: Color = Color.Black,
) = WeatherColors(sectionTextColor, sectionBgColor, false)

fun lightWeatherColors(
    sectionTextColor: Color = Color.Black,
    sectionBgColor: Color = Color.White,
) = WeatherColors(sectionTextColor, sectionBgColor, true)

internal val LocalWeatherColors = staticCompositionLocalOf<WeatherColors> {
    error("No DemoColorPalette provided")
}
