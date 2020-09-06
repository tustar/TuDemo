package com.tustar.demo.data

import android.location.Location
import com.tustar.demo.data.remote.CaiyunClient.service

object WeatherRepository {

    suspend fun searchPlace(query: String) = service.searchPlace(query)
    suspend fun getRealtime(location: Location) = service.getRealtime(
        location.longitude,
        location.latitude
    )
}