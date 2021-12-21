package com.tustar.data.source


import com.tustar.data.Weather
import com.tustar.data.source.remote.City

interface WeatherRepository {
    suspend fun weather(location: String, poiName: String): Weather
    suspend fun cityTop():List<City>
}