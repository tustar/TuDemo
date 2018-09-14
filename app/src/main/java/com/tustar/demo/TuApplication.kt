package com.tustar.demo

import android.app.Application
import android.content.Context
import com.tustar.common.util.DeviceUtils
import com.tustar.common.util.Logger
import com.tustar.demo.proxy.ProxyUtils

/**
 * Created by tustar on 17-6-15.
 */
class TuApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        Logger.d(TAG, "attachBaseContext:7")
        super.attachBaseContext(base)
        ProxyUtils.hookNotificationManager(this)
    }

    override fun onCreate() {
        super.onCreate()
        var processName = DeviceUtils.getProcessName(applicationContext)
        Logger.d(TAG, "processName = $processName")

        CrashHandler().init(this)
    }

    companion object {

        private var TAG = TuApplication::class.simpleName
    }
}