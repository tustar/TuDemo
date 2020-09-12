package com.tustar.demo.data

import android.location.Location
import com.tustar.demo.data.remote.AmapClient
import com.tustar.demo.data.remote.CaiyunClient

object WeatherRepository {

    suspend fun searchPlace(query: String) = CaiyunClient.service.searchPlace(query)
    suspend fun getRealtime(location: Location) = CaiyunClient.service.getRealtime(
        location.longitude,
        location.latitude
    )

    suspend fun geocode(location: Location) = AmapClient.service.geocode(
        "${location.longitude},${location.latitude}"
    )
}