package com.tustar.ktstudy

import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.coroutines.*
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED


suspend fun suspendOne() = suspendCoroutineUninterceptedOrReturn<String> {
    thread {
        TimeUnit.SECONDS.sleep(1)
        Logger.d("suspendOne::我将调用Continuation返回一个数据")
        //调用又会重新调用BaseContinuationImpl的resumeWith函数
        it.resume("hello world1")
    }

    Logger.d("suspendOne::函数返回")
    COROUTINE_SUSPENDED
}

suspend fun safeSuspendTwo() = suspendCoroutine<String> {
    thread {
        TimeUnit.SECONDS.sleep(1)
        Logger.d("safeSuspendTwo::我将调用Continuation返回一个数据")
        //调用又会重新调用BaseContinuationImpl的resumeWith函数
        it.resume("hello world2")
    }

    Logger.d("safeSuspendTwo::函数返回")
}

fun main() {

    val suspendLambda: suspend () -> String = {
        suspendOne()
        safeSuspendTwo()
    }

    val completion = MyCoroutine()
    suspendLambda.startCoroutine(completion)
    Logger.d("启动协程,由于挂起函数延迟返回结果,所以这句话会先打出来")
}


