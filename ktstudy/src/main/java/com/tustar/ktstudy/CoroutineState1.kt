package com.tustar.ktstudy

import kotlin.coroutines.*


suspend fun suspendFun1(): String {
    return "[hello world 1] "
}

suspend fun suspendFun2(): String {
    return "[hello world 2]  "
}

suspend fun suspendFun3(): String {
    return "[hello world 3]"
}

fun main() {
    val suspendLambda: suspend () -> String = {
        val one = suspendFun1()
        val two = suspendFun2()
        val three = suspendFun3()
        one + two + three
    }

    val completion = MyContinuation()
    /**
     *  调用顺序
     *  block.startCoroutine(completion) ->
     *  TempContinuation(SuspendLambda子类).resumeWith ->
     *  BaseContinuationImpl.resumeWith ->
     *  TempContinuation.invokeSuspend
     */
    suspendLambda.startCoroutine(completion)
}


