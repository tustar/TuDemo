package com.tustar.demo.data.source

import com.amap.api.location.AMapLocation
import com.tustar.demo.data.Weather

interface WeatherRepository {
    suspend fun weather(location: AMapLocation): Weather
}