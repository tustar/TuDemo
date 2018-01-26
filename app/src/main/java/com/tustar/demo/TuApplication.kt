package com.tustar.demo

import android.app.Application
import android.content.Context
import com.tustar.demo.proxy.ProxyUtils
import com.tustar.demo.util.DeviceUtils
import com.tustar.demo.util.Logger

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
        Logger.d(TAG, "processName = " + processName)

        CrashHandler().init(this    )
    }
}