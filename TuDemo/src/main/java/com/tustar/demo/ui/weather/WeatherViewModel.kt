package com.tustar.demo.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.amap.api.location.AMapLocation
import com.tustar.demo.data.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor() : ViewModel() {

    private val locationLiveData = MutableLiveData<AMapLocation>()

    val now = Transformations.switchMap(locationLiveData) {
        liveData(Dispatchers.IO) {
            emit(WeatherRepository.now(it))
        }
    }
    val hourly = Transformations.switchMap(locationLiveData) {
        liveData(Dispatchers.IO) {
            emit(WeatherRepository.hourly(it))
        }
    }

    fun now(location: AMapLocation) {
        locationLiveData.value = location
    }
}