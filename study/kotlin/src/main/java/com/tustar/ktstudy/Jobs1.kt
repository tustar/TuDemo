package com.tustar.ktstudy

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

suspend fun main() {
    // Job1
    val job1 = GlobalScope.launch {
        //获取当前的上下文
        val job = coroutineContext[Job]
        Logger.d("协程内部job:$job")
    }
    //协程返回的job和内部或者是同一对象
    Logger.d("协程外部的job:$job1")

    // Job2
    Logger.d("----------------------")
    val job2 = GlobalScope.launch(start = CoroutineStart.LAZY) {
        TimeUnit.MILLISECONDS.sleep(500)
        Logger.d("协程完成")
    }
    Logger.d(
        "启动前:isActive=${job2.isActive}, isCompleted=${job2.isCompleted}, " +
                "isCancelled=${job2.isCancelled}"
    )
    job2.start()
    Logger.d(
        "启动后:isActive=${job2.isActive}, isCompleted=${job2.isCompleted}, " +
                "isCancelled=${job2.isCancelled}"
    )
    job2.cancel()
    Logger.d(
        "取消后:isActive=${job2.isActive}, isCompleted=${job2.isCompleted}, " +
                "isCancelled=${job2.isCancelled}"
    )
    job2.join()
    Logger.d(
        "协程完成:isActive=${job2.isActive}, isCompleted=${job2.isCompleted}, " +
                "isCancelled=${job2.isCancelled}"
    )

    TimeUnit.HOURS.sleep(1)
}