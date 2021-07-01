package com.tustar.demo.data

import com.amap.api.location.AMapLocation
import com.tustar.demo.data.remote.Now
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun now(location: AMapLocation): Flow<Now>
}