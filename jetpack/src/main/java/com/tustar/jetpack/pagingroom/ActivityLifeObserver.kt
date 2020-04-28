package com.tustar.demo.ui.jet.pagingroom

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.tustar.util.Logger

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