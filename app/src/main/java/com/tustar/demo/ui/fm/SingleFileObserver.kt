package com.tustar.demo.ui.fm

import android.text.TextUtils
import com.tustar.util.Logger
import java.io.File

/**
 * Created by tustar on 17-7-17.
 */
class SingleFileObserver(var path: String) {

    companion object {
        private val TAG = SingleFileObserver::class.java.simpleName
    }

    private var observers = hashMapOf<String, FmFileObserver>()

    fun startWatching() {
        startWatching(path)
    }

    fun startWatching(path: String) {
        Logger.d(TAG, "stopWatching :: stopWatching $path")
        if (TextUtils.isEmpty(path)) {
            return
        }

        var observer = FmFileObserver(path)
        observers.put(path, observer)
        observer.startWatching()
        var file = File(path)
        if (file.isDirectory) {
            file.listFiles().forEach { startWatching(it.path) }
        }
    }

    fun stopWatching() {
        Logger.d(TAG, "stopWatching :: stopWatching all paths")
        if (observers.isEmpty()) {
            return
        }

        synchronized(observers, {
            observers.values.forEach { it.stopWatching() }
        })
    }

    fun stopWatching(path: String?) {
        Logger.d(TAG, "stopWatching :: stopWatching $path")
        if (TextUtils.isEmpty(path)) {
            return
        }

        if (observers.isEmpty()) {
            return
        }

        var observer = observers.remove(path)
        if (observer != null) {
            observer.stopWatching()
        }
    }
}