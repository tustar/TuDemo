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
        val air5D = service.air5D(location).daily
        val hourly24H = service.weather24H(location).hourly
        val daily15D = service.weather15D(location).daily
        val indices1D = service.indices1D(location).daily
        return Weather(
            weatherNow = weatherNow,
            warning = warning,
            airNow = airNow,
            air5D = air5D,
            hourly24H = hourly24H,
            daily15D = daily15D,
            indices1D = indices1D,
        )
    }

    override suspend fun geoTopCity(): List<City> {
        return service.geoTopCity().topCityList
    }

    override suspend fun geoCityLookup(location: String): List<City> {
        return service.geoCityLookup(location).location
    }
}