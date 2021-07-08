package com.tustar.demo.data.source

import com.amap.api.location.AMapLocation
import com.tustar.demo.data.source.remote.HeService
import com.tustar.demo.ex.toParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val service: HeService
) : WeatherRepository {

    override
    suspend fun now(location: AMapLocation) = flow {
        val response = service.now(location.toParams()).now
        emit(response)
    }.flowOn(Dispatchers.IO)
}