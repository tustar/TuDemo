package com.tustar.demo.data.source

import com.amap.api.location.AMapLocation
import com.tustar.demo.data.Weather
import com.tustar.demo.data.source.remote.HeService
import com.tustar.demo.ex.toParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val service: HeService
) : WeatherRepository {

    override
    suspend fun weather(location: AMapLocation): Weather {
        val now = service.now(location.toParams()).now
        return Weather(location.poiName, now.temp, now.text)
    }
}