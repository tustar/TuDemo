package com.tustar.demo.ex

import android.Manifest
import android.app.AppOpsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.LocationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun Context.isSystemApp(): Boolean {
    val mask = ApplicationInfo.FLAG_SYSTEM or ApplicationInfo.FLAG_UPDATED_SYSTEM_APP
    val pkgInfo = packageManager.getPackageInfo(packageName, 0)
    return pkgInfo.applicationInfo.flags and mask != 0
}

fun Context.getStringByName(name: String): String {
    val resId = resources.getIdentifier(name, "string", packageName)
    return getString(resId)
}

fun Context.getDrawableByName(name: String): Drawable? {
    val resId = resources.getIdentifier(name, "drawable", packageName)
    return ContextCompat.getDrawable(this, resId)

}

fun Context.getVersionName(): String =
    packageManager.getPackageInfo(packageName, PackageManager.GET_CONFIGURATIONS).versionName

fun Context.isLocationEnable(): Boolean {
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    val network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    return gps || network
}

@RequiresApi(Build.VERSION_CODES.Q)
fun Context.isPermissionsAllowed(permissions: Array<String>): Boolean {
    val appOpsManager = getSystemService(AppCompatActivity.APP_OPS_SERVICE) as AppOpsManager
    val uid = packageManager.getPackageUid(packageName, 0)
    return permissions.all {
        appOpsManager.unsafeCheckOp(it, uid, packageName) == AppOpsManager.MODE_ALLOWED
    }
}

fun Context.isPermissionsGranted(permissions: Array<String>): Boolean {
    return permissions.all {
        ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }
}
