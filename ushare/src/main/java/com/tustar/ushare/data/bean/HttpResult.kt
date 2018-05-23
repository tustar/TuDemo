package com.tustar.ushare.data.bean

import com.tustar.ushare.util.CommonDefine

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

data class User(var nick: String,
                var mobile: String,
                var token: String,
                var shared: Boolean = false,
                var type: String = CommonDefine.USER,
                var weight: Int = 0) {

    companion object {

    }
}


data class Code(var v_code: String)

