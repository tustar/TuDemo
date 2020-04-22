package com.tustar.util

import android.os.Build
import android.util.Log

/**
 * Created by tustar on 15-10-14.
 */
object Logger {

    private const val APP_TAG = "TuDemo"

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
        when (Build.TYPE) {
            VERSION_USER -> type = TYPE_ALL
            VERSION_ENG, VERSION_USER_DEBUG -> type = TYPE_ALL
            else -> Log.e(APP_TAG, "unknown build type, type = " + Build.TYPE)
        }
    }

    private val msgPrefix: String
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
        if (type and TYPE_V != 0) {
            Log.v(APP_TAG, "$msgPrefix $msg")
        }
    }

    @JvmStatic
    fun v(tag: String? = "", msg: String? = "") {
        v(msg)
    }

    @JvmStatic
    fun d(msg: String? = "") {
        if (type and TYPE_D != 0) {
            Log.d(APP_TAG, "$msgPrefix $msg")
        }
    }

    @JvmStatic
    fun d(tag: String? = "", msg: String? = "") {
        d(msg)
    }

    @JvmStatic
    fun i(msg: String? = "") {
        if (type and TYPE_I != 0) {
            Log.i(APP_TAG, "$msgPrefix $msg")
        }
    }

    @JvmStatic
    fun i(tag: String? = "", msg: String? = "") {
        i(msg)
    }

    @JvmStatic
    fun w(msg: String? = "") {
        if (type and TYPE_W != 0) {
            Log.w(APP_TAG, "$msgPrefix $msg")
        }
    }

    @JvmStatic
    fun w(tag: String? = "", msg: String? = "") {
        w(msg)
    }

    @JvmStatic
    fun e(msg: String? = "") {
        if (type and TYPE_E != 0) {
            Log.e(APP_TAG, "$msgPrefix $msg")
        }
    }

    @JvmStatic
    fun e(tag: String? = "", msg: String? = "") {
        e(msg)
    }

    @JvmStatic
    fun e(msg: String? = "", tr: Throwable) {
        if (type and TYPE_E != 0) {
            Log.e(APP_TAG, "$msgPrefix $msg", tr)
        }
    }

    @JvmStatic
    fun e(tag: String? = "", msg: String? = "", tr: Throwable) {
        e(msg, tr)
    }
}
