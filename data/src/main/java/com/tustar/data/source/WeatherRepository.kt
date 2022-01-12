package com.tustar.data.source


import com.tustar.data.Weather
import com.tustar.data.source.remote.City
import com.tustar.data.source.remote.WeatherNow

interface WeatherRepository {
    suspend fun weatherNow(location: String): WeatherNow
    suspend fun weather(location: String): Weather
    suspend fun geoTopCity():List<City>
    suspend fun geoCityLookup(location: String):List<City>
}