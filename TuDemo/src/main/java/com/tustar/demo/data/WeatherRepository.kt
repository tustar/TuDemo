package com.tustar.demo.data

import com.amap.api.location.AMapLocation
import com.tustar.demo.data.remote.HeClient
import com.tustar.demo.ui.weather.toParams

object WeatherRepository {

    suspend fun now(location: AMapLocation) = HeClient.service.now(location.toParams())
    suspend fun hourly(location: AMapLocation) = HeClient.service.hourly(location.toParams())
}