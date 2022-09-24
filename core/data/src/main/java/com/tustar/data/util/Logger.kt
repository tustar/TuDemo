package com.tustar.data.util

import android.os.Build
import android.util.Log

/**
 * Created by tustar on 15-10-14.
 *
 * When Build.TYPE is VERSION_USER, you can use adb shell setprop log.tag.TuDemo_Data D open log
 */
object Logger {

    private const val APP_TAG = "TuDemo_Data"

    private val packageName = Logger::class.java.name

    private const val TYPE_V = 1
    private const val TYPE_D = 1 shl 1
    private const val TYPE_I = 1 shl 2
    private const val TYPE_W = 1 shl 3
    private const val TYPE_E = 1 shl 4
    private const val TYPE_ALL = TYPE_V or TYPE_D or TYPE_I or TYPE_W or TYPE_E

    private var type = TYPE_ALL

    private const val VERSION_ENG = "eng"
    private const val VERSION_USER = "user"
    private const val VERSION_USER_DEBUG = "userdebug"


    init {
        Log.i(APP_TAG, "Build.TYPE = " + Build.TYPE)
        when (Build.TYPE) {
            VERSION_USER -> type = TYPE_W
            VERSION_ENG, VERSION_USER_DEBUG -> type = TYPE_ALL
            else -> Log.e(APP_TAG, "unknown build type, type = " + Build.TYPE)
        }
    }

    private val prefix: String
        get() {
            try {
                Throwable().stackTrace.forEach {
                    if (!it.className.startsWith(packageName)) {
                        val className = it.className.substringAfterLast(".")
                        val funcName = it.methodName
                        val lineNumb = it.lineNumber
                        return "$className::$funcName[$lineNumb]"
                    }
                }
            } catch (e: Exception) {
                Log.e(APP_TAG, "log failed", e)
            }
            return ""
        }

    @JvmStatic
    fun v(msg: String? = "") {
        if ((type and TYPE_V != 0) or Log.isLoggable(APP_TAG, Log.VERBOSE)) {
            Log.v(APP_TAG, "$prefix $msg")
        }
    }

    @JvmStatic
    fun d(msg: String? = "") {
        if ((type and TYPE_D != 0) or Log.isLoggable(APP_TAG, Log.DEBUG)) {
            Log.d(APP_TAG, "$prefix $msg")
        }
    }

    @JvmStatic
    fun i(msg: String? = "") {
        if ((type and TYPE_I != 0) or Log.isLoggable(APP_TAG, Log.INFO)) {
            Log.i(APP_TAG, "$prefix $msg")
        }
    }


    @JvmStatic
    fun w(msg: String? = "") {
        if ((type and TYPE_W != 0) or Log.isLoggable(APP_TAG, Log.WARN)) {
            Log.w(APP_TAG, "$prefix $msg")
        }
    }

    @JvmStatic
    fun e(msg: String? = "") {
        if ((type and TYPE_E != 0) or Log.isLoggable(APP_TAG, Log.ERROR)) {
            Log.e(APP_TAG, "$prefix $msg")
        }
    }


    @JvmStatic
    fun e(msg: String? = "", tr: Throwable) {
        if ((type and TYPE_E != 0) or Log.isLoggable(APP_TAG, Log.ERROR)) {
            Log.e(APP_TAG, "$prefix $msg", tr)
        }
    }
}

