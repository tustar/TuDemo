package com.tustar.ushare.data.bean

data class HttpResult<out D, out E>(
        val data: D,
        val message: String,
        val status: Int,
        val extra: E
) {
    companion object {
        const val OK = 200
        const val FAILURE = 500
    }
}

data class User(val id: Int,
                val mobile: String,
                val code: String,
                val token: String,
                val weight: Int,
                val shared: Boolean,
                val nick: String,
                val last_at: Int,
                val next_at: Int) {

    companion object {

    }
}


data class Code(var v_code: String)
