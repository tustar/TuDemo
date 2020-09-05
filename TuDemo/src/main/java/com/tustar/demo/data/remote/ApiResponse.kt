package com.tustar.demo.data.remote

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

data class ApiResponse<out T>(val code: String, val msg: String, val data: T)

typealias SuccessBlock<T> = suspend CoroutineScope.(T) -> Unit
typealias ErrorBlock = suspend CoroutineScope.(String) -> Unit

suspend fun <T : Any> ApiResponse<T>.doSuccess(block: SuccessBlock<T>? = null): ApiResponse<T> {
    return coroutineScope {
        if (code == "ok") {
            block?.invoke(this, data)
        }
        this@doSuccess
    }

}

suspend fun <T : Any> ApiResponse<T>.doError(block: ErrorBlock? = null): ApiResponse<T> {
    return coroutineScope {
        if (code != "ok") {
            block?.invoke(this, msg)
        }
        this@doError
    }
}

