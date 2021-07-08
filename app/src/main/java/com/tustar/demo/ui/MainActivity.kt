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

import android.Manifest
import android.annotation.SuppressLint
import android.app.AppOpsManager
import android.content.Context
import android.os.Binder
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.tustar.demo.ex.*
import com.tustar.demo.util.LocationHelper
import com.tustar.demo.util.Logger
import dagger.hilt.android.AndroidEntryPoint


const val KEY_LOCATION = "location"

@SuppressLint("NewApi")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // Location
    private val locationListener by lazy {
        LocationListener()
    }
    private val locationHelper by lazy {
        LocationHelper(applicationContext).apply {
            setLocationListener { location ->
                Logger.d("location=${location.toFormatString()}")
                if (location.errorCode == 0) {
                    viewModel.liveLocation.value = location
                }
            }
        }
    }

    //
    private val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
//        Manifest.permission.ACCESS_BACKGROUND_LOCATION
    )
    private val opstrs = arrayOf(
        AppOpsManager.OPSTR_FINE_LOCATION,
        AppOpsManager.OPSTR_COARSE_LOCATION
    )
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->


            if (map.values.all { it }) {
                getBestLocation()
            }
        }

    //
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This app draws behind the system bars, so we want to handle fitting system windows
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            DemoApp(viewModel, { getBestLocation() }) { finish() }
        }

        lifecycle.addObserver(locationListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(locationListener)
    }

    private fun requestPermissions() {
        if (containsIgnore(opstrs)) {
            Logger.d("containsIgnore")
            viewModel.liveResult.value = LocationPermissionResult(
                show = true,
                rationale = true
            )
            return
        }

        if (shouldShowRequestPermissionsRationale(permissions)) {
            Logger.d("shouldShowRequestPermissionsRationale")
            requestPermissionLauncher.launch(permissions)
            return
        }

        if (!isPermissionsGranted(permissions)) {
            Logger.d("!isPermissionsGranted")
            requestPermissionLauncher.launch(permissions)
            return
        }
    }

    private fun getBestLocation() {
        Logger.i()
        if (!isPermissionsGranted(permissions)) {
            requestPermissions()
            return
        }

        val show = !isLocationEnable()
        viewModel.liveResult.value = LocationPermissionResult(show)
        if (show) {
            return
        }

        locationHelper.startLocation()
    }

    inner class LocationListener : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun start() {
            Logger.i()
            // connect
            getBestLocation()
        }


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
