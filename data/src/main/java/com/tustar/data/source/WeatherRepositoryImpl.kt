package com.tustar.data.source

import com.tustar.data.Weather
import com.tustar.data.source.remote.HeService
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val service: HeService
) : WeatherRepository {

    override
    suspend fun weather(location: String, poiName: String): Weather {
        val now = service.now(location).now
        return Weather(poiName, now)
    }
}