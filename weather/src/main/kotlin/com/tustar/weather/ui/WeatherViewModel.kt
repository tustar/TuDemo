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

    private val _homeWeather = MutableStateFlow<HomeWeather?>(null)
    val homeWeather: StateFlow<HomeWeather?> = _homeWeather
    private val _weather = MutableStateFlow<CityWeather?>(null)
    val weather: StateFlow<CityWeather?> = _weather
    private val _current = MutableStateFlow<Location>(Location.getDefaultInstance())

    //
    val current: StateFlow<Location> = _current
    private val _cities = MutableStateFlow<MutableMap<String, Location>>(mutableMapOf())
    val cities: StateFlow<MutableMap<String, Location>> = _cities
    private val _topCities = MutableStateFlow<List<City>>(emptyList())
    val topCities: StateFlow<List<City>> = _topCities
    private val _searchCities = MutableStateFlow<List<City>>(emptyList())
    val searchCities: StateFlow<List<City>> = _searchCities

    private val _weatherPrefs = MutableStateFlow(WeatherPrefs.getDefaultInstance())
    val weatherPrefs: StateFlow<WeatherPrefs> = _weatherPrefs

    fun requestLocateWeather(locate: Location) {
        viewModelScope.launch {
            val weatherNow = weatherRepository.weatherNow(locate.toParams())
            _homeWeather.value = HomeWeather(locate, weatherNow.temp, weatherNow.text)
        }
    }

    fun requestWeather(context: Context, location: Location) {
        viewModelScope.launch {
            _weather.value = CityWeather(
                location,
                weatherRepository.weather(location.toParams())
            )
            updateLastUpdated(context, System.currentTimeMillis())
        }
    }

    fun weatherPrefs(context: Context) {
        viewModelScope.launch {
            weatherPrefsFlow(context).collect { prefs ->
                _weatherPrefs.value = prefs
                _cities.value = mutableMapOf<String, Location>().apply {
                    if (prefs.locate.isValid()) {
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

    fun saveMode24H(context: Context, mode24H: WeatherPrefs.Mode) {
        viewModelScope.launch {
            updateMode24H(context, mode24H)
        }
    }

    fun saveMode15D(context: Context, mode15D: WeatherPrefs.Mode) {
        viewModelScope.launch {
            updateMode15D(context, mode15D)
        }
    }

    fun updateLocate(
        context: Context,
        lon: String,
        lat: String,
        name: String,
        adm1: String = "",
        adm2: String = "",
    ) {
        val locate = Location.newBuilder()
            .setLat(lat)
            .setLon(lon)
            .setName(name)
            .setAuto(true)
            .setAdm1(adm1)
            .setAdm2(adm2)
            .build()
        _current.value = locate
        viewModelScope.launch {
            updateLocate(context, locate)
            requestLocateWeather(locate)
        }
    }

    fun requestTopCities() {
        viewModelScope.launch {
            _topCities.value = weatherRepository.geoTopCity()
        }
    }

    fun searchCities(location: String) {
        viewModelScope.launch {
            if (location.isNullOrEmpty()) {
                _searchCities.value = emptyList()
            } else {
                _searchCities.value = weatherRepository.geoCityLookup(location)
            }
        }
    }

    fun clearSearch() {
        viewModelScope.launch {
            _searchCities.value = emptyList()
        }
    }

    fun updateCurrent(location: Location) {
        viewModelScope.launch {
            _current.value = location
        }
    }

    fun addCity(context: Context, city: City) {
        viewModelScope.launch {
            val location = city.toLocation()
            _current.value = location
            addCity(context, location)
            _cities.value = _cities.value.apply {
                put(city.name, city.toLocation())
            }
        }
    }

    fun removeCity(context: Context, city: Location) {
        viewModelScope.launch {
            com.tustar.weather.util.removeCity(context, city)
            _cities.value = _cities.value.apply {
                remove(city.name)
            }
        }
    }
}

data class HomeWeather(
    val locate: Location,
    val temp: Int,
    val text: String,
)

data class CityWeather(
    val location: Location,
    val weather: Weather,
)