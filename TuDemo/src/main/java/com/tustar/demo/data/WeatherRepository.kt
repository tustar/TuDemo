package com.tustar.demo.data

import com.tustar.demo.data.remote.CaiyunClient.service

object WeatherRepository {

    suspend fun searchPlace(query: String) = service.searchPlace(query)
}