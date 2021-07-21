package com.tustar.demo.data.source

import com.amap.api.location.AMapLocation
import com.tustar.demo.data.Weather
import com.tustar.demo.data.source.remote.HeService
import com.tustar.demo.ktx.toParams
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