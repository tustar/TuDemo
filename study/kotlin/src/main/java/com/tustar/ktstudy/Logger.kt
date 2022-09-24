package com.tustar.ktstudy

import java.text.SimpleDateFormat
import java.util.*

object Logger {
    @Suppress("SimpleDateFormat")
    fun d(message: Any?) {
        val date = SimpleDateFormat("yy-MM-dd HH:mm:ss.SSS").format(Date())
        println("$date [${Thread.currentThread().name}] $message")
    }
}