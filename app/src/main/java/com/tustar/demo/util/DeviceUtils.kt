package com.tustar.demo.util

import android.app.ActivityManager
import android.content.Context
import android.os.Process


/**
 * Created by tustar on 11/5/16.
 */
object DeviceUtils {

    fun getProcessName(context: Context): String? {
        var pid = Process.myPid()
        var am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        am.runningAppProcesses.forEach {
            if (it.pid == pid) {
                return it.processName
            }
        }

        return null
    }
}