package com.tustar.ktstudy

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

fun main() {

    val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        Logger.d("Throws an exception with message: ${exception.message}")
    }

    val job = GlobalScope.launch(exceptionHandler) {
        Thread.sleep(100)
        Logger.d("协程完成")
    }

    val disposable1 = job.invokeOnCompletion {
        Logger.d("disposable1完成")
    }
    val disposable2 = job.invokeOnCompletion {
        Logger.d("disposable2完成")
    }
    val disposable3 = job.invokeOnCompletion {
        Logger.d("disposable3完成")
    }
    TimeUnit.SECONDS.sleep(1)
    Logger.d("完成")
}