package com.tustar.retrofit2.data.bean

data class HttpResult<out D, out E>(
        val data: D,
        val msg: String,
        val status: Int,
        val extra: E
) {
    companion object {
        const val OK = 200
        const val FAILURE = 500
    }
}

data class User(var name: String,
                var password: String)

data class Code(var v_code: String)

