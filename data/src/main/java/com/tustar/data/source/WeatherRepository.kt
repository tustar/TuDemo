package com.tustar.data.source


import com.tustar.data.Weather

interface WeatherRepository {
    suspend fun weather(location: String, poiName: String): Weather
}