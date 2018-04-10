package com.tustar.common.util

import android.app.ActivityManager
import android.content.Context
import android.net.ConnectivityManager
import android.os.Process
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager


/**
 * Created by tustar on 11/5/16.
 */
object DeviceUtils {

    fun getProcessName(context: Context): String? {
        var pid = Process.myPid()
        var am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return am.runningAppProcesses.firstOrNull { it.pid == pid }?.processName
    }

    fun getScreenMetrics(context: Context): DisplayMetrics {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm
    }

    fun dp2px(context: Context, dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.resources.displayMetrics)
    }

    fun isWifi(context: Context): Boolean {
        val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.activeNetworkInfo
        return activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_WIFI
    }
}