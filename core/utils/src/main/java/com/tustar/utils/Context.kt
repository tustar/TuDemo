package com.tustar.utils

import android.app.Activity
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.LocationManager
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.provider.Settings
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

fun Context.getStringId(name: String):Int {
   return resources.getIdentifier(name, "string", packageName)
}

fun Context.getDrawableId(name: String): Int {
    return resources.getIdentifier(name, "drawable", packageName)
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

fun Context.containsIgnoreOpstrs(opstrs: Array<String>): Boolean {
    val appOpsManager = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
    return opstrs.any {
        appOpsManager.checkOpNoThrow(
            it,
            Binder.getCallingUid(),
            packageName
        ) == AppOpsManager.MODE_IGNORED
    }
}

fun Context.containsIgnoreOpstr(opstr: String): Boolean {
    val appOpsManager = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
    return appOpsManager.checkOpNoThrow(
        opstr,
        Binder.getCallingUid(),
        packageName
    ) == AppOpsManager.MODE_IGNORED
}

fun Activity.shouldShowRequestPermissionsRationale(permissions: Array<String>): Boolean {
    return permissions.any {
        ActivityCompat.shouldShowRequestPermissionRationale(this, it)
    }
}

fun Context.isPermissionsGranted(permissions: Array<String>): Boolean {
    return permissions.all {
        ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }
}

fun Context.actionLocationSourceSettings() {
    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
}

fun Context.actionWirelessSettings() {
    startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
}

fun Context.actionApplicationDetailsSettings() {
    startActivity(
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        )
    )
}
