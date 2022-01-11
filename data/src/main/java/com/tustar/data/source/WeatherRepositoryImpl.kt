package com.tustar.data.source

import com.tustar.data.Weather
import com.tustar.data.source.remote.City
import com.tustar.data.source.remote.HeService
import com.tustar.data.source.remote.WeatherNow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val service: HeService,
) : WeatherRepository {

    override suspend fun weatherNow(location: String): WeatherNow {
        return service.weatherNow(location).now
    }

    override
    suspend fun weather(location: String): Weather {
        val weatherNow = service.weatherNow(location).now
        val warning = service.warningNow(location).warning
        val airNow = service.airNow(location).now
        val air5d = service.air5d(location).daily
        val hourly24h = service.weather24h(location).hourly
        val daily15d = service.weather15d(location).daily
        val indices = service.indices(location).daily
        return Weather(
            weatherNow = weatherNow,
            warning = warning,
            airNow = airNow,
            air5d = air5d,
            hourly24h = hourly24h,
            daily15d = daily15d,
            indices = indices,
        )
    }

    override suspend fun cityTop(): List<City> {
        return service.cityTop().topCityList
    }

    override suspend fun cityLookup(location: String): List<City> {
        return service.cityLookup(location).location
    }
}