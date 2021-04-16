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
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationListener
import com.tustar.demo.data.remote.Now
import com.tustar.demo.ui.theme.DemoTheme
import com.tustar.demo.util.LocationHelper
import com.tustar.demo.util.Logger
import dagger.hilt.android.AndroidEntryPoint


const val KEY_LOCATION = "location"

@SuppressLint("NewApi")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // Location
    private val locationLifecycleObserver by lazy {
        LocationLifecycleObserver()
    }
    private val liveLocation = MutableLiveData<AMapLocation>()
    val locationHelper by lazy {
        LocationHelper(applicationContext)
    }
    private val viewModel: MainViewModel by viewModels()
    private var now: Now? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This app draws behind the system bars, so we want to handle fitting system windows
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            DemoTheme {
                DemoApp(onBackPressedDispatcher)
            }
        }

        lifecycle.addObserver(locationLifecycleObserver)
        viewModel.now.observe(this, {
            now = it
        })

        requestPermissions()
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(locationLifecycleObserver)
    }

    private fun requestPermissions() {
        val shouldProvideRationale =
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) || ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Logger.i("Displaying permission rationale to provide additional context.")
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        } else {
            Logger.i("Requesting permission")
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                getBestLocation()
            }
        }
    }

    private fun getBestLocation() {
        Logger.i()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions()
            return
        }

        locationHelper.startLocation(AMapLocationListener { location ->
            // 可在其中解析location获取相应内容。
            if (location.errorCode == 0) {
                Logger.d("location=$location")
                liveLocation.run { postValue(location) }
            }
            // 定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
            else {
                Logger.e(
                    "location Error, ErrCode:${location.errorCode}, " +
                            "errInfo:${location.errorInfo}"
                )
            }
        })
    }

    inner class LocationLifecycleObserver : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun onStart() {
            Logger.i()
            getBestLocation()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun onStop() {
            Logger.i()
            locationHelper.stopLocation()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            Logger.i()
            locationHelper.onDestroy()
        }
    }

    companion object {
        private const val REQUEST_PERMISSIONS_REQUEST_CODE = 0x1
    }
}

