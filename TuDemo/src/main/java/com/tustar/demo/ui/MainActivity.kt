package com.tustar.demo.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.OneTimeWorkRequestBuilder
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tustar.demo.R
import com.tustar.demo.ui.weather.WeatherWorker
import com.tustar.demo.util.Logger
import com.tustar.ktx.navigateUpOrFinish
import dagger.hilt.android.AndroidEntryPoint


@SuppressLint("NewApi")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navController by lazy {
        findNavController(R.id.nav_host_fragment)
    }
    private val locationLifecycleObserver by lazy {
        LocationLifecycleObserver()
    }
    val liveLocation = MutableLiveData<Location>()
    //
    private val locationManager: LocationManager by lazy {
        getSystemService(LocationManager::class.java)
    }
    private val locationCallback = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Logger.i("location=$location")
            updateLocation(location)
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            Logger.i("$provider status:$status, extra:$extras")
        }

        override fun onProviderEnabled(provider: String) {
            Logger.i("$provider")
        }

        override fun onProviderDisabled(provider: String) {
            Logger.i("$provider")
        }
    }

    private val weatherWorkRequest by lazy {
        OneTimeWorkRequestBuilder<WeatherWorker>()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
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
        val criteria = Criteria()
        val provider = locationManager.getBestProvider(criteria, true)
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

        val location = if (provider.isNullOrEmpty()) {
            if (LocationManagerCompat.isLocationEnabled(locationManager)) {
                locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            } else {
                null
            }
        } else {
            locationManager.getLastKnownLocation(provider)
        }
        if (location == null) {
            val provider = LocationManager.PASSIVE_PROVIDER
            val location = locationManager.getLastKnownLocation(provider)
            if (location != null) {
                updateLocation(location)
            }
            locationManager.requestLocationUpdates(provider, 60000L, 1.0F,
                locationCallback)
        } else {
            updateLocation(location)
        }
    }

    private fun updateLocation(location: Location) {
        Logger.i("location=$location")
        liveLocation.postValue(location)
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
            locationManager.removeUpdates(locationCallback)
        }
    }

    companion object {
        private const val REQUEST_PERMISSIONS_REQUEST_CODE = 0x1
    }
}