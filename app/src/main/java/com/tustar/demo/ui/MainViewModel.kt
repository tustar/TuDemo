package com.tustar.demo.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.amap.api.location.AMapLocation
import com.tustar.demo.data.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val locationLiveData = MutableLiveData<AMapLocation>()

    val now = Transformations.switchMap(locationLiveData) {
        liveData {
            weatherRepository.now(it)
                .onStart { }
                .catch { }
                .collectLatest {
                    emit(it)
                }
        }
    }
}