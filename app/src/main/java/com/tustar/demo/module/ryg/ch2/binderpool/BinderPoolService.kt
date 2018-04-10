package com.tustar.demo.module.ryg.ch2.binderpool

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.tustar.common.util.Logger

/**
 * Created by tustar on 17-7-24.
 */
class BinderPoolService : Service() {

    companion object {
        private val TAG = BinderPoolService::class.java.simpleName
    }

    var mBinderPool = BinderPool.BinderPoolImpl()

    override fun onCreate() {
        Logger.i(TAG, "onCreate")
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder {
        Logger.i(TAG, "onBind")
        return mBinderPool
    }

    override fun onDestroy() {
        Logger.i(TAG, "onDestroy")
        super.onDestroy()
    }
}