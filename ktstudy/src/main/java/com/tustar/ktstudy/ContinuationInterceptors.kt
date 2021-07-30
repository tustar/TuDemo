package com.tustar.ktstudy

import kotlin.concurrent.thread
import kotlin.coroutines.*

suspend fun hello() = suspendCoroutine<String> { c ->
    thread(name = "Hello线程") {
        Logger.d("hello函数调用resume回调")
        c.resume("hello")
    }
}

suspend fun world() = suspendCoroutine<String> { c ->
    thread(name = "World线程") {
        Logger.d("world函数调用resume回调")
        c.resume("world")
    }
}

fun main() {
    val suspendLambda = suspend {
        Logger.d("hello函数运行前")
        val hello = hello()
        Logger.d("hello函数运行后")
        val world = world()
        Logger.d("world函数运行后")
        "$hello $world"
    }

    val completion = MyContinuation()

    thread(name = "测试线程") {
        suspendLambda.startCoroutine(completion)
    }
}

class MyContinuation : Continuation<String> {
    override val context: CoroutineContext = MyCoroutineDispatch()
    override fun resumeWith(result: Result<String>) {
        Logger.d("MyCoroutine回调resumeWith返回 ${result.getOrNull()}")
    }
}

class MyCoroutineDispatch : AbstractCoroutineContextElement(ContinuationInterceptor),
    ContinuationInterceptor {

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        Logger.d("interceptContinuation")
        return MyInterceptorContinuation<T>(continuation.context, continuation)
    }

    override fun releaseInterceptedContinuation(continuation: Continuation<*>) {
        super.releaseInterceptedContinuation(continuation)

        Logger.d("releaseInterceptedContinuation ${continuation::class.java.simpleName}")
    }

    class MyInterceptorContinuation<T>(
        override val context: CoroutineContext,
        private val continuation: Continuation<T>
    ) : Continuation<T> {
        override fun resumeWith(result: Result<T>) {
            thread(name = "main") {
                Logger.d("MyInterceptorContinuation resume")
                continuation.resumeWith(result)
            }
        }
    }
}