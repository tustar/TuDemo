package com.tustar.demo.util

import android.content.Context
import android.content.pm.PackageManager


object DeviceUtils {
    fun getVersionName(context: Context): String {
        val pm = context.packageManager
        val pi = pm.getPackageInfo(
            context.packageName,
            PackageManager.GET_CONFIGURATIONS
        )
        return pi.versionName
    }
}