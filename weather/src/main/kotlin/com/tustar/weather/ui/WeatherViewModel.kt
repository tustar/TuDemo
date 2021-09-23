package com.tustar.weather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amap.api.location.AMapLocation
import com.tustar.data.Weather
import com.tustar.data.source.WeatherRepository
import com.tustar.weather.ktx.toParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
open class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {


    private val _weather = MutableStateFlow<Weather?>(null)
    val weather: StateFlow<Weather?> = _weather

    fun requestWeather(location: AMapLocation) {
        viewModelScope.launch {
            _weather.value = weatherRepository.weather(location.toParams(), location.poiName)
        }
    }

    fun onWeatherChange(weather: Weather) {
        _weather.value = weather
    }
}