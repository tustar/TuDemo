package com.tustar.ktstudy

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class MyCoroutine : Continuation<String> {
    override val context: CoroutineContext = EmptyCoroutineContext
    override fun resumeWith(result: Result<String>) {
        Logger.d("MyCoroutine回调resumeWith返回 ${result.getOrNull()}")
    }
}