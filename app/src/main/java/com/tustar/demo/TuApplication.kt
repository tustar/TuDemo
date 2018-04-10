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

    companion object {
        private var TAG = TuApplication::class.simpleName
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        ProxyUtils.hookNotificationManager(this)
    }

    override fun onCreate() {
        super.onCreate()
        var processName = DeviceUtils.getProcessName(applicationContext)
        Logger.d(TAG, "processName = $processName")

        CrashHandler().init(this    )
    }
}