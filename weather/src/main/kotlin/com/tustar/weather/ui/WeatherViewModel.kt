package com.tustar.weather.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tustar.data.Weather
import com.tustar.data.source.WeatherRepository
import com.tustar.weather.WeatherPrefs
import com.tustar.weather.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
open class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _weather = MutableStateFlow<Weather?>(null)
    val weather: StateFlow<Weather?> = _weather

    private val _weatherPrefs = MutableStateFlow(WeatherPrefs.getDefaultInstance())
    val weatherPrefs: StateFlow<WeatherPrefs> = _weatherPrefs

    fun requestWeather(location: String, poiName: String) {
        viewModelScope.launch {
            _weather.value = weatherRepository.weather(location, poiName)
        }
    }

    fun onWeatherChange(weather: Weather) {
        _weather.value = weather
    }

    fun weatherPrefs(context: Context) {
        viewModelScope.launch {
            weatherPrefsFlow(context).collect {
                _weatherPrefs.value = it
            }
        }
    }

    fun onList24h(context: Context, isList: Boolean) {
        viewModelScope.launch {
            updateList24H(context, isList)
        }
    }

    fun onList15d(context: Context, isList: Boolean) {
        viewModelScope.launch {
            updateList15D(context, isList)
        }
    }

    fun onUpdateLocation(context: Context, location: String, poi: String) {
        viewModelScope.launch {
            updateLocation(context, location, poi)
        }
        requestWeather(location, poi)
    }
}