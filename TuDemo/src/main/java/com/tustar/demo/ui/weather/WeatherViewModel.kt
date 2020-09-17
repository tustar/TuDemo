package com.tustar.demo.ui.weather

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.amap.api.location.AMapLocation
import com.tustar.demo.data.WeatherRepository
import kotlinx.coroutines.Dispatchers

class WeatherViewModel @ViewModelInject constructor() : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()
    private val locationLiveData = MutableLiveData<AMapLocation>()

    val places = Transformations.switchMap(searchLiveData) {
        liveData(Dispatchers.IO) {
            emit(WeatherRepository.searchPlace(it).places)
        }
    }
    val realtime = Transformations.switchMap(locationLiveData) {
        liveData(Dispatchers.IO) {
            emit(WeatherRepository.getRealtime(it).result.realtime)
        }
    }

    fun getRealtime(location: AMapLocation) {
        locationLiveData.value = location
    }
}