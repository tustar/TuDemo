package com.tustar.demo.ui.ryg.ch10

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.tustar.common.util.Logger
import com.tustar.demo.R
import com.tustar.demo.base.BaseBookActivity

class RygCh10Activity : BaseBookActivity() {

    companion object {
        private val TAG = RygCh10Activity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.ryg_ch10_title)

        val mBooleanThreadLocal = ThreadLocal<Boolean>()
        mBooleanThreadLocal.set(true)
        Logger.d(TAG, "onCreate ::  ${Thread.currentThread().name} mBooleanThreadLocal = " +
                "${mBooleanThreadLocal.get()}")

        Thread({
            mBooleanThreadLocal.set(false)
            Logger.d(TAG, "onCreate ::  ${Thread.currentThread().name} mBooleanThreadLocal = " +
                    "${mBooleanThreadLocal.get()}")
        }, "Thread#1").start()

        Thread({
            Logger.d(TAG, "onCreate ::  ${Thread.currentThread().name} mBooleanThreadLocal = " +
                    "${mBooleanThreadLocal.get()}")
        }, "Thread#2").start()

        Thread({
            Looper.prepare()
            val handler = Handler()
            Looper.loop()
        }, "Thread#3").start()
    }
}
