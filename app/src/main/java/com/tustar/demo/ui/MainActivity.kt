/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tustar.demo.ui

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.work.*
import com.tustar.demo.R
import com.tustar.demo.ktx.*
import com.tustar.demo.ui.recorder.OnRecorderListener
import com.tustar.demo.ui.recorder.RecorderInfo
import com.tustar.demo.ui.recorder.RecorderService
import com.tustar.demo.util.LocationHelper
import com.tustar.demo.util.Logger
import com.tustar.demo.woker.WeatherWorker
import dagger.hilt.android.AndroidEntryPoint


@SuppressLint("NewApi")
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnRecorderListener {

    // Location
    private val locationListener by lazy {
        LocationListener()
    }
    private val locationHelper by lazy {
        LocationHelper(applicationContext).apply {
            setLocationListener { location ->
                Logger.d("location=${location.toFormatString()}")
                if (location.errorCode == 0) {
                    WeatherWorker.doRequest(this@MainActivity, location, viewModel)
                }
            }
        }
    }

    private var recorderService: RecorderService? = null
    private var conn: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Logger.i()
            val binder = service as RecorderService.RecorderBinder
            recorderService = binder.getService()
            recorderService?.addOnRecorderListener(this@MainActivity)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Logger.i()
        }
    }

    //
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This app draws behind the system bars, so we want to handle fitting system windows
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            DemoApp(
                viewModel,
                updateLocation = { locationHelper.startLocation() }
            )
        }

        lifecycle.addObserver(locationListener)
        RecorderService.bindService(this, conn)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Logger.d("intent=$intent")
    }

    override fun onRecorderChanged(info: RecorderInfo) {
        viewModel.liveRecorderInfo.value = info
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(locationListener)
        recorderService?.removeOnRecorderListener(this)
        unbindService(conn)
    }

    inner class LocationListener : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun stop() {
            // disconnect if connected
            Logger.i()
            locationHelper.stopLocation()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun destroy() {
            Logger.i()
            locationHelper.onDestroy()
        }
    }
}
