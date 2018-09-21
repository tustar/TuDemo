package com.tustar.demo.module.jet.paging

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import com.tustar.common.util.Logger

class ActivityLifeObserver : BaseActivityPresenter, LifecycleObserver {

    companion object {
        private const val TAG = "ActivityLifeObserver"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate() {
        Logger.i(TAG, "")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun onStart() {
        Logger.i(TAG, "")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onResume() {
        Logger.i(TAG, "")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onPause() {
        Logger.i(TAG, "")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onStop() {
        Logger.i(TAG, "")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        Logger.i(TAG, "")
    }
}