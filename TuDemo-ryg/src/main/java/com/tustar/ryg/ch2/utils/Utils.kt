package com.tustar.ryg.ch2.utils

import java.io.Closeable
import java.io.IOException


/**
 * Created by tustar on 17-6-26.
 */
object Utils {
    fun close(closeable: Closeable?) {
        try {
            closeable?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun executeInThread(runnable: Runnable) {
        Thread(runnable).start()
    }
}