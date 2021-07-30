package com.tustar.ktstudy

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class MyContinuation(coroutineContext: CoroutineContext = EmptyCoroutineContext) :
    Continuation<String> {
    override val context: CoroutineContext = coroutineContext
    override fun resumeWith(result: Result<String>) {
        Logger.d("MyCoroutine回调resumeWith返回 ${result.getOrNull()}")
    }
}