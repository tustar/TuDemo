package com.tustar.demo.ui.weather

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.amap.api.location.AMapLocation
import com.tustar.demo.data.WeatherRepository
import com.tustar.demo.ui.KEY_LOCATION
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope

@HiltWorker
class WeatherWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {


    override suspend fun doWork(): Result = coroutineScope {

        val location = inputData.keyValueMap[KEY_LOCATION] as AMapLocation
//        WeatherRepository.now(location)
        Result.success()
    }
}