package com.tustar.data.source


import com.tustar.data.Weather
import com.tustar.data.source.remote.City
import com.tustar.data.source.remote.Location
import com.tustar.data.source.remote.WeatherNow

interface WeatherRepository {
    suspend fun weatherNow(location: String): WeatherNow
    suspend fun weather(location: String): Weather
    suspend fun cityTop():List<City>
    suspend fun cityLookup(location: String):List<Location>
}