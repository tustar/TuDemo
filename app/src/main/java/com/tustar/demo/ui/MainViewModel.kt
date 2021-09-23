package com.tustar.demo.ui

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.amap.api.location.AMapLocation
import com.tustar.data.DemoItem
import com.tustar.data.codegen.generateDemos
import com.tustar.data.source.WeatherRepository
import com.tustar.demo.ui.recorder.RecorderInfo
import com.tustar.demo.util.doNotShowFlow
import com.tustar.demo.util.updateDoNotShow
import com.tustar.weather.ui.WeatherViewModel
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
    weatherRepository: WeatherRepository
) : WeatherViewModel(weatherRepository) {

    private val _demos = MutableStateFlow<Map<Int, List<DemoItem>>>(emptyMap())
    val demos: StateFlow<Map<Int, List<DemoItem>>> = _demos

    private var _aMapLocation = MutableStateFlow<AMapLocation?>(null)
    val aMapLocation: StateFlow<AMapLocation?> = _aMapLocation

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

    fun onUpdateLocation(location: AMapLocation?) {
        _aMapLocation.value = location
        aMapLocation.value?.let {
            requestWeather(it)
        }
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

