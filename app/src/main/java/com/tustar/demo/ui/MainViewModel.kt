package com.tustar.demo.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amap.api.location.AMapLocation
import com.tustar.data.DemoItem
import com.tustar.data.Weather
import com.tustar.data.codegen.generateDemos
import com.tustar.data.source.WeatherRepository
import com.tustar.demo.ktx.toParams
import com.tustar.demo.ui.recorder.RecorderInfo
import com.tustar.demo.util.doNotShowFlow
import com.tustar.demo.util.updateDoNotShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _demos = MutableStateFlow<Map<Int, List<DemoItem>>>(emptyMap())
    val demos: StateFlow<Map<Int, List<DemoItem>>> = _demos

    private val _updateLocation = MutableStateFlow(false)
    val updateLocation: StateFlow<Boolean> = _updateLocation
    private val _weather = MutableStateFlow<Weather?>(null)
    val weather: StateFlow<Weather?> = _weather

    private val _recorderInfo = MutableStateFlow(RecorderInfo())
    val recorderInfo: StateFlow<RecorderInfo> = _recorderInfo

    private val _doNotShow = MutableStateFlow(false)
    val doNotShow: StateFlow<Boolean> = _doNotShow

    init {
        viewModelScope.launch {
            val data = generateDemos().groupBy { it.group }
            _demos.value = data
        }
    }

    fun onUpdateLocation(updateLocation: Boolean) {
        _updateLocation.value = updateLocation
    }

    fun requestWeather(location: AMapLocation) {
        viewModelScope.launch {
            _weather.value = weatherRepository.weather(location.toParams(), location.poiName)
        }
    }

    fun onWeatherChange(weather: Weather) {
        _weather.value = weather
    }

    fun onRecorderInfoChange(info: RecorderInfo) {
        _recorderInfo.value = info
    }

    fun doNotShow(context: Context) {
        viewModelScope.launch {
            doNotShowFlow(context).flowOn(Dispatchers.IO).collect {
                _doNotShow.value = it.doNotShow
            }
        }
    }

    fun onDoNotShow(context: Context, doNotShow: Boolean) {
        viewModelScope.launch {
            _doNotShow.value = doNotShow
            updateDoNotShow(context, doNotShow)
        }
    }
}

