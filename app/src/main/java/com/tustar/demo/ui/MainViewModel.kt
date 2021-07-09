package com.tustar.demo.ui

import androidx.lifecycle.*
import com.amap.api.location.AMapLocation
import com.tustar.demo.data.source.WeatherRepository
import com.tustar.demo.codegen.generateDemos
import com.tustar.demo.data.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    val liveWeather = MutableLiveData<Weather>()
    val liveResult = MutableLiveData<AppOpsResult>()

    fun createDemos() = flow {
        emit(generateDemos().groupBy { it.group })
    }
}

data class AppOpsResult(
    var visible: Boolean = false,
    var rationale: Boolean = false
)