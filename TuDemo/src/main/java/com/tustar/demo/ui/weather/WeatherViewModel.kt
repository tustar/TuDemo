package com.tustar.demo.ui.weather

import android.content.Context
import android.location.Location
import androidx.lifecycle.*
import com.tustar.demo.data.WeatherRepository
import kotlinx.coroutines.Dispatchers


class WeatherViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(context) as T
    }
}

class WeatherViewModel(context: Context) : ViewModel() {

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

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }

    fun getRealtime(location: Location) {
        locationLiveData.value = location
    }
}