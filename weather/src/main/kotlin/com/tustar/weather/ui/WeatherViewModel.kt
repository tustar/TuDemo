package com.tustar.weather.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tustar.data.Weather
import com.tustar.data.source.WeatherRepository
import com.tustar.data.source.remote.City
import com.tustar.weather.Location
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
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    private val _locateWeather = MutableStateFlow<LocateWeather?>(null)
    val locateWeather: StateFlow<LocateWeather?> = _locateWeather
    private val _weather = MutableStateFlow<Weather?>(null)
    val weather: StateFlow<Weather?> = _weather
    private val _cities = MutableStateFlow<MutableMap<String, Location>>(mutableMapOf())
    val cities: StateFlow<MutableMap<String, Location>> = _cities
    private val _topCities = MutableStateFlow<List<City>>(emptyList())
    val topCities: StateFlow<List<City>> = _topCities

    private val _weatherPrefs = MutableStateFlow(WeatherPrefs.getDefaultInstance())
    val weatherPrefs: StateFlow<WeatherPrefs> = _weatherPrefs

    fun requestLocateWeather(context: Context, locate: Location) {
        viewModelScope.launch {
            val weatherNow = weatherRepository.weatherNow(locate.toParams())
            _locateWeather.value = LocateWeather(locate, weatherNow.temp, weatherNow.text)
        }
    }

    fun requestWeather(context: Context, location: Location) {
        viewModelScope.launch {
            _weather.value = weatherRepository.weather(location.toParams())
            updateLastUpdated(context, System.currentTimeMillis())
            weatherRepository.cityTop()
        }
    }

    fun onWeatherChange(weather: Weather) {
        _weather.value = weather
    }

    fun weatherPrefs(context: Context) {
        viewModelScope.launch {
            weatherPrefsFlow(context).collect { prefs ->
                _weatherPrefs.value = prefs
                _cities.value = mutableMapOf<String, Location>().apply {
                    if(prefs.locate.isValid()) {
                        put(prefs.locate.name, prefs.locate)
                    }

                    prefs.citiesMap.values.forEach { location ->
                        if (location.isValid()) {
                            put(location.name, location)
                        }
                    }
                }
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

    fun onUpdateLocate(
        context: Context, lon: String, lat: String, poi: String,
    ) {
        val locate = Location.newBuilder()
            .setLat(lat)
            .setLon(lon)
            .setName(poi)
            .setAuto(true)
            .build()
        viewModelScope.launch {
            updateLocate(context, locate)
            requestLocateWeather(context, locate)
        }
    }

    fun requestTopCities() {
        viewModelScope.launch {
            _topCities.value = weatherRepository.cityTop()
        }
    }

    fun onAddCity(context: Context, city: Location) {
        viewModelScope.launch {
            addCity(context, city)
            _cities.value = _cities.value.apply {
                put(city.name, city)
            }
        }
    }

    fun onRemoveCity(context: Context, city: Location) {
        viewModelScope.launch {
            removeCity(context, city)
            _cities.value = _cities.value.apply {
                remove(city.name)
            }
        }
    }
}

data class LocateWeather(
    val locate: Location,
    val temp: Int,
    val text: String,
)