package com.tustar.demo.ui

import androidx.lifecycle.*
import com.amap.api.location.AMapLocation
import com.tustar.demo.data.WeatherRepository
import com.tustar.demo.data.gen.generateDemos
import com.tustar.demo.ui.weather.Weather
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

    fun createDemos() = flow {
        emit(generateDemos().groupBy { it.group })
    }
}