package com.tustar.demo.module.ryg.ch2.utils

import android.app.Activity
import android.graphics.Color
import java.io.Closeable
import java.io.IOException
import android.os.Bundle
import com.tustar.demo.R


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