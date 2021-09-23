package com.tustar.demo.woker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.amap.api.location.AMapLocation
import com.google.gson.Gson
import com.tustar.data.Weather
import com.tustar.data.source.WeatherRepository
import com.tustar.demo.ui.MainViewModel
import com.tustar.weather.ktx.toParams
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope


@HiltWorker
class WeatherWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val weatherRepository: WeatherRepository
) : CoroutineWorker(appContext, workerParams) {


    override suspend fun doWork(): Result = coroutineScope {
        try {
            val gson = Gson()
            val location =
                gson.fromJson(inputData.getString(KEY_LOCATION), AMapLocation::class.java)
            val weather = weatherRepository.weather(location.toParams(), location.poiName)
            val outputData = workDataOf(KEY_WEATHER to gson.toJson(weather))
            Result.success(outputData)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

    companion object {
        const val KEY_LOCATION = "location"
        const val KEY_WEATHER = "weather"

        fun requestWeather(
            activity: AppCompatActivity,
            location: AMapLocation,
            viewModel: MainViewModel
        ) {
            val gson = Gson()
            val inputData = workDataOf(KEY_LOCATION to gson.toJson(location))
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val oneTimeWorkRequest = OneTimeWorkRequestBuilder<WeatherWorker>()
                .setConstraints(constraints)
                .setInputData(inputData)
                .build()
            val workManager = WorkManager.getInstance(activity.applicationContext)
            workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.id).observe(activity) {
                if (it.state == WorkInfo.State.SUCCEEDED) {
                    val weather = gson.fromJson(
                        it.outputData.getString(KEY_WEATHER),
                        Weather::class.java
                    )
                    viewModel.onWeatherChange(weather)
                }
            }
            workManager.enqueue(oneTimeWorkRequest)
        }
    }
}