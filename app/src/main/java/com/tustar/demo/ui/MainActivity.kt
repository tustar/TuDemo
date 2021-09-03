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
import androidx.compose.material.primarySurface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.lifecycle.*
import androidx.work.*
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tustar.demo.ktx.*
import com.tustar.demo.ui.recorder.OnRecorderListener
import com.tustar.demo.ui.recorder.RecorderInfo
import com.tustar.demo.ui.recorder.RecorderService
import com.tustar.demo.ui.theme.DemoTheme
import com.tustar.demo.util.LocationHelper
import com.tustar.demo.util.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


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
                    viewModel.onUpdateLocation(false)
                    viewModel.requestWeather(location)
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
            ProvideWindowInsets {
                DemoApp(viewModel)
            }
        }
        lifecycle.addObserver(locationListener)
        RecorderService.bindService(this, conn)

        // Start a coroutine in the lifecycle scope
        lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Trigger the flow and start listening for values.
                // Note that this happens when lifecycle is STARTED and stops
                // collecting when the lifecycle is STOPPED
                viewModel.updateLocation.collect {
                    if (it) {
                        locationHelper.startLocation()
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Logger.d("intent=$intent")
    }

    override fun onRecorderChanged(info: RecorderInfo) {
        viewModel.onRecorderInfoChange(info)
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
