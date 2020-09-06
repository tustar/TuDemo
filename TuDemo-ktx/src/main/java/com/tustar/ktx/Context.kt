package com.tustar.ktx

import android.content.Context
import android.content.pm.ApplicationInfo

fun Context.isSystemApp(): Boolean {
    val mask = ApplicationInfo.FLAG_SYSTEM or ApplicationInfo.FLAG_UPDATED_SYSTEM_APP
    val pkgInfo = packageManager.getPackageInfo(packageName, 0)
    return pkgInfo.applicationInfo.flags and mask != 0
}

fun Context.getStringByName(name: String): String {
    val resId = resources.getIdentifier(name, "string", packageName)
    return getString(resId)
}
