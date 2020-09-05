package com.tustar.demo.ui.weather

import android.content.Context
import androidx.lifecycle.*
import com.tustar.demo.data.WeatherRepository
import com.tustar.demo.data.remote.Place
import kotlinx.coroutines.Dispatchers


class WeatherViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(context) as T
    }
}

class WeatherViewModel(context: Context) : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val places = Transformations.switchMap(searchLiveData) {
        liveData<List<Place>>(Dispatchers.IO) {
            val places = WeatherRepository.searchPlace(it).places
            emit(places)
        }
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }
}