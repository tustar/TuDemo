package com.tustar.demo.data.source

import com.amap.api.location.AMapLocation
import com.tustar.demo.data.source.remote.Now
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun now(location: AMapLocation): Flow<Now>
}