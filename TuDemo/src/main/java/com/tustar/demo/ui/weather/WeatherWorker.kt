package com.tustar.demo.ui.weather

import android.content.Context
import android.location.Location
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tustar.demo.data.WeatherRepository
import com.tustar.demo.ui.KEY_LOCATION
import kotlinx.coroutines.coroutineScope

class WeatherWorker @WorkerInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {


    override suspend fun doWork(): Result = coroutineScope {

        val location = inputData.keyValueMap[KEY_LOCATION] as Location
        WeatherRepository.getRealtime(location).result.realtime
        Result.success()
    }
}