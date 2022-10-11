package com.tustar.weather.ui

import com.tustar.data.Weather
import com.tustar.weather.WeatherPrefs

class WeatherContact {

    data class State(
        val weather: Weather?,
        val prefs: WeatherPrefs,
        val loading: Boolean = false,
    )

    sealed class Effect {
        object DataWasLoaded : Effect()
    }
}