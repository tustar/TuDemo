package com.tustar.demo.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tustar.demo.R
import com.tustar.demo.util.LocationHelper
import com.tustar.demo.util.Logger
import com.tustar.ktx.navigateUpOrFinish
import dagger.hilt.android.AndroidEntryPoint

const val TAB_INDEX_HOME = 0
const val TAB_INDEX_ARTICLE = 1
const val TAB_INDEX_TODO = 2
const val TAB_INDEX_WEATHER = 3
const val KEY_TAB_INDEX_SELECTED = "tab_index_selected"

//
const val KEY_LOCATION = "location"

@SuppressLint("NewApi")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navController by lazy {
        findNavController(R.id.nav_host_fragment)
    }
    private lateinit var navView: BottomNavigationView

    // Location
    private val locationLifecycleObserver by lazy {
        LocationLifecycleObserver()
    }
    val liveLocation = MutableLiveData<AMapLocation>()
    val locationHelper by lazy {
        LocationHelper(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navView = findViewById(R.id.nav_view)
        Logger.d()

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_article,
                R.id.navigation_todo,
                R.id.navigation_weather
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController)

        //
        lifecycle.addObserver(locationLifecycleObserver)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        updateSelectedTab(intent)
    }

    private fun updateSelectedTab(intent: Intent) {
        val selectedTabIndex = intent.getIntExtra(KEY_TAB_INDEX_SELECTED, TAB_INDEX_ARTICLE)
        navView.selectedItemId = selectedTabIndex
    }

    override fun onSupportNavigateUp() = navController.navigateUpOrFinish(this)

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
                liveLocation.postValue(location)
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