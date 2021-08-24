package com.tustar.demo.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amap.api.location.AMapLocation
import com.tustar.demo.codegen.generateDemos
import com.tustar.demo.data.DemoItem
import com.tustar.demo.data.Weather
import com.tustar.demo.ui.AppOpsResult.Companion.OPS_TAG_LOCATION
import com.tustar.demo.ui.recorder.RecorderInfo
import com.tustar.demo.woker.WeatherWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _demos = MutableStateFlow<Map<Int, List<DemoItem>>>(emptyMap())
    val demos: StateFlow<Map<Int, List<DemoItem>>> = _demos

    private val _updateLocation = MutableStateFlow(true)
    val updateLocation: StateFlow<Boolean> = _updateLocation
    private val _weather = MutableStateFlow<Weather?>(null)
    val weather: StateFlow<Weather?> = _weather

    private val _opsResult = MutableStateFlow(AppOpsResult(OPS_TAG_LOCATION))
    val opsResult: StateFlow<AppOpsResult> = _opsResult

    private val _recorderInfo = MutableStateFlow(RecorderInfo())
    val recorderInfo: StateFlow<RecorderInfo> = _recorderInfo

    init {
        viewModelScope.launch {
            val data = generateDemos().groupBy { it.group }
            _demos.value = data
        }
    }

    fun onUpdateLocation(updateLocation: Boolean) {
        _updateLocation.value = updateLocation
    }

    fun requestWeather(activity: AppCompatActivity, location: AMapLocation) {
        WeatherWorker.requestWeather(activity, location, this)
    }

    fun onWeatherChange(weather: Weather) {
        _weather.value = weather
    }

    fun onOpsResultChange(opsResult: AppOpsResult) {
        _opsResult.value = opsResult
    }

    fun onRecorderInfoChange(info: RecorderInfo) {
        _recorderInfo.value = info
    }
}

