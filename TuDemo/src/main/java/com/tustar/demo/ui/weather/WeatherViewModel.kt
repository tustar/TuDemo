package com.tustar.demo.ui.weather

import android.location.Location
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.tustar.demo.data.WeatherRepository
import kotlinx.coroutines.Dispatchers

class WeatherViewModel @ViewModelInject constructor() : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()
    private val locationLiveData = MutableLiveData<Location>()

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
    val address = Transformations.switchMap(locationLiveData) {
        liveData(Dispatchers.IO) {
            emit(WeatherRepository.geocode(it).regeocode.formattedAddress)
        }
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }

    fun getRealtime(location: Location) {
        locationLiveData.value = location
    }
}