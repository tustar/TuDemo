package com.tustar.common.util

import android.Manifest
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Process
import android.support.v4.app.ActivityCompat
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager


/**
 * Created by tustar on 11/5/16.
 */
object DeviceUtils {

    @JvmStatic
    fun getProcessName(context: Context): String? {
        var pid = Process.myPid()
        var am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return am.runningAppProcesses.firstOrNull { it.pid == pid }?.processName
    }

    @JvmStatic
    fun getScreenMetrics(context: Context): DisplayMetrics {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm
    }

    @JvmStatic
    fun dp2px(context: Context, dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.resources.displayMetrics)
    }

    @JvmStatic
    fun isWifi(context: Context): Boolean {
        val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.activeNetworkInfo
        return activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_WIFI
    }

    @JvmStatic
    fun getDeviceId(context: Context, key: String="device_id"): String? {

        var deviceId = PreferencesUtils.getString(context, key,
                null)
        if (!TextUtils.isEmpty(deviceId)) {
            return deviceId
        }

        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED) {

            deviceId = when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ->
                    tm.getImei(tm.phoneCount - 1)
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ->
                    tm.getDeviceId(tm.phoneCount - 1)
                else -> tm.deviceId
            }

            PreferencesUtils.putString(context, key, deviceId)
        }

        return deviceId
    }
}