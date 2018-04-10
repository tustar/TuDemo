package com.tustar.demo.module.fm

import android.os.FileObserver
import android.text.TextUtils
import com.tustar.common.util.Logger

/**
 * Created by tustar on 17-7-17.
 */
class FmFileObserver(path: String?, mask: Int) : FileObserver(path, mask) {

    companion object {
        private val TAG = FileObserver::class.java.simpleName
    }

    constructor(path: String?) : this(path, mask = ALL_EVENTS)

    override fun onEvent(event: Int, path: String?) {
        Logger.d(TAG, "onEvent :: path = $path")
        if (TextUtils.isEmpty(path)) {
            return
        }
        when (event) {
            ACCESS -> {
                Logger.d(TAG, "ACCESS")
            }
            MODIFY -> {
                Logger.d(TAG, "MODIFY")
            }
            ATTRIB -> {
                Logger.d(TAG, "ATTRIB")
            }
            CLOSE_WRITE -> {
                Logger.d(TAG, "CLOSE_WRITE")
            }
            CLOSE_NOWRITE -> {
                Logger.d(TAG, "CLOSE_NOWRITE")
            }
            OPEN -> {
                Logger.d(TAG, "OPEN")
            }
            MOVED_FROM -> {
                Logger.d(TAG, "MOVED_FROM")
            }
            MOVED_TO -> {
                Logger.d(TAG, "MOVED_TO")
            }
            CREATE -> {
                Logger.d(TAG, "CREATE")
            }
            DELETE -> {
                Logger.d(TAG, "DELETE")
            }
            DELETE_SELF -> {
                Logger.d(TAG, "DELETE_SELF")
            }
            MOVE_SELF -> {
                Logger.d(TAG, "MOVE_SELF")
            }
        }
    }
}