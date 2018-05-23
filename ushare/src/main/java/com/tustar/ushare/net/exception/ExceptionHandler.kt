package com.tustar.ushare.net.exception

import com.google.gson.JsonParseException
import com.tustar.common.util.Logger
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

class ExceptionHandler {

    companion object {

        var code = StatusCode.UNKNOWN_ERROR

        fun handleException(e: Throwable): Int {
            Logger.e(e.toString())
            when (e) {
                is HttpException -> {
                    code = e.code()
                }
            // 网络
                is SocketTimeoutException ->
                    code = StatusCode.SOCKET_TIMEOUT_ERROR

                is ConnectException ->
                    code = StatusCode.CONNECT_ERROR

                is UnknownHostException ->
                    code = StatusCode.UNKNOWN_HOST_ERROR

            // 解析
                is JsonParseException, is JSONException, is ParseException
                -> code = StatusCode.PARSE_ERROR

            // 服务器
                is ApiException ->
                    code = StatusCode.API_ERROR

                is IllegalArgumentException -> code = StatusCode.SERVER_ERROR
            // 未知错误
                else -> code = StatusCode.UNKNOWN_ERROR
            }
            return code
        }
    }
}