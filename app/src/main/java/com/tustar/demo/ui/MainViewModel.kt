package com.tustar.demo.ui

import androidx.lifecycle.*
import com.amap.api.location.AMapLocation
import com.tustar.demo.data.source.WeatherRepository
import com.tustar.demo.codegen.generateDemos
import com.tustar.demo.data.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    val liveLocation = MutableLiveData<AMapLocation>()
    val now = liveLocation.switchMap { location ->
        liveData {
            weatherRepository.now(location)
                .onStart { }
                .catch { }
                .collectLatest { now ->
                    emit(Weather(location.poiName, now.temp, now.text))
                }
        }
    }

    val liveResult = MutableLiveData<LocationPermissionResult>()

    fun createDemos() = flow {
        emit(generateDemos().groupBy { it.group })
    }
}

data class LocationPermissionResult(
    var show: Boolean = false,
    var rationale: Boolean = false
)