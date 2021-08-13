package com.tustar.ktstudy

import kotlinx.coroutines.*

fun main() {
    val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Logger.d(("Handle $throwable in CoroutineExceptionHandler"))
    }
    val topLevelScope = CoroutineScope(Job())
    topLevelScope.launch {
        /**
         * 1
         */
//        launch {
//            throw RuntimeException("RuntimeException in coroutine")
//        }
        /**
         * 2
         */
//        try {
//            coroutineScope {
//                launch {
//                    throw RuntimeException("RuntimeException in nested coroutine")
//                }
//            }
//        } catch (exception: Exception) {
//            println("Handle $exception in try/catch")
//        }
        /**
         * 3
         */
        val job1 = launch {
            println("starting Coroutine 1")
        }

        supervisorScope {
            val job2 = launch(coroutineExceptionHandler) {
                println("starting Coroutine 2")
                throw RuntimeException("Exception in Coroutine 2")
            }

            val job3 = launch {
                println("starting Coroutine 3")
            }
        }
    }
    Thread.sleep(100)
}