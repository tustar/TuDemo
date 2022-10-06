package com.tustar.weather.ui

import android.annotation.SuppressLint
import android.content.Context
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tustar.common.Result
import com.tustar.common.asResult
import com.tustar.data.Weather
import com.tustar.data.source.WeatherRepository
import com.tustar.utils.Logger
import com.tustar.weather.WeatherPrefs
import com.tustar.weather.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    private var _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    @SuppressLint("MissingPermission")
    fun getLocation(context: Context) {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val criteria = Criteria().apply {
            accuracy = Criteria.ACCURACY_FINE// 高精度
            isAltitudeRequired = false// 不要求海拔
            isBearingRequired = false// 不要求方位
            isCostAllowed = true// 允许有花费
            powerRequirement = Criteria.POWER_LOW// 低功耗
        }
        // 从可用的位置提供器中，匹配以上标准的最佳提供器
        val provider = locationManager.getBestProvider(criteria, true)
            ?: LocationManager.NETWORK_PROVIDER

        /**
         * adb -s emulator-5554 emu geo fix 116.39744 39.90874
         */
        var location = locationManager.getLastKnownLocation(provider)
        requestWeather(context, location)
        val listener = LocationListener {
            location = it
            requestWeather(context, location)
        }
        locationManager.requestLocationUpdates(provider, 2000, 10.0F, listener)
    }

    private fun requestWeather(context: Context, location: Location?) {
        Logger.d("location:$location")
        if (location == null) {
            return
        }
        viewModelScope.launch {
            val prefsStream = weatherPrefsFlow(context)
            val weatherStream = flow {
                emit(weatherRepository.weather(location.toParams()))
            }
            combine(prefsStream, weatherStream, ::Pair)
                .asResult()
                .map { result ->
                    when (result) {
                        is Result.Success -> {
                            val (prefs, weather) = result.data
                            WeatherUiState.Success(prefs, weather)
                        }
                        is Result.Loading -> {
                            WeatherUiState.Loading
                        }
                        is Result.Error -> {
                            WeatherUiState.Error
                        }
                    }
                }
                .collect {
                    _uiState.value = it
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
}

sealed interface WeatherUiState {
    data class Success(
        val prefs: WeatherPrefs,
        val weather: Weather,
    ) : WeatherUiState

    object Error : WeatherUiState
    object Loading : WeatherUiState
}
