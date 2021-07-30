package com.tustar.ktstudy

import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.resume
import kotlin.coroutines.startCoroutine


suspend fun suspendThree() = suspendCoroutineUninterceptedOrReturn<String> {
    it.resume("hello world3")
    Logger.d("虽然协程返回了结果，但是我依然要打印")
//    COROUTINE_SUSPENDED
    "返回, 会出现Crash"
}

fun main() {

    val suspendLambda = suspend {
        val result = suspendThree()
        Logger.d("返回的结果是$result")
        result
    }

    val completion = MyContinuation()
    suspendLambda.startCoroutine(completion)
}


