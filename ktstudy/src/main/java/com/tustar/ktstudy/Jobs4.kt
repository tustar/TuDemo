package com.tustar.ktstudy

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val job1 = launch { // ①
        Logger.d(1)
        try {
            delay(1000) // ②
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Logger.d(2)
    }
    delay(100)
    Logger.d(3)
    job1.cancel() // ③
    Logger.d(4)
}