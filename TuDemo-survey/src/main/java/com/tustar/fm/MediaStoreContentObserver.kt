package com.tustar.fm

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import com.tustar.util.Logger
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by tustar on 17-7-17.
 */
class MediaStoreContentObserver(var handler: Handler?) : ContentObserver(handler) {

    companion object {
        private val TAG = MediaStoreContentObserver::class.java.simpleName
    }

    private var start: Long = 0
    private var executor: ScheduledThreadPoolExecutor? = null
    private var uri: Uri? = null

    constructor(handler: Handler, context: Context, executor: ScheduledThreadPoolExecutor,
                uri: Uri) : this(handler) {
        this.executor = executor
        this.uri = uri
        context.contentResolver.registerContentObserver(uri, true, this)
    }

    override fun onChange(selfChange: Boolean) {
        var begin = start
        start = System.currentTimeMillis()
        if (start - begin < 1000L) {
            Logger.d(TAG, "Merge all events in 1 second")
            return
        }

        this.executor!!.schedule({}, 3L, TimeUnit.SECONDS)
    }
}