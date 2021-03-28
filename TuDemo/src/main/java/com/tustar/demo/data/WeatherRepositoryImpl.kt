package com.tustar.demo.data

import com.amap.api.location.AMapLocation
import com.tustar.demo.data.remote.HeService
import com.tustar.demo.data.remote.Now
import com.tustar.demo.ui.weather.toParams
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val service: HeService
) : WeatherRepository {

    override
    suspend fun now(location: AMapLocation) =
        flow<Now> {
            service.now(location.toParams()).now
        }
}