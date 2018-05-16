package com.tustar.retrofit2.net.exception

object StatusCode {

    /**
     * -1 未知异常
     */
    const val UNKNOWN_ERROR = -1

    /**
     * 200 响应成功
     */
    const val SUCCESS = 200

    /**
     * 400 请求参数缺失/格式错误/验签失败
     */
    const val PARAMS_ERROR = 400

    /**
     * 401 Token已失效
     */
    const val TOKEN_EXPIRED = 401

    /**
     * 405 请求方法不支持
     */
    const val METHOD_UNSUPPORT = 405

    /**
     * 415 内容类型不支持
     */
    const val CONTENT_UNSUPPORT = 415

    /**
     * 500 服务器内部错误
     */
    const val SERVER_ERROR = 500

    /**
     * 1001 网络连接超时
     */
    const val NETWORK_ERROR = 1001

    /**
     * 1002 解析出错
     */
    const val PARSE_ERROR = 1002

    /**
     * 1003 API出错
     */
    const val API_ERROR = 1003

    /**
     * 1004 socket连接超时
     */
    const val SOCKET_TIMEOUT_ERROR = 1004

    /**
     * 1005 socket连接不上
     */
    const val CONNECT_ERROR = 1005

    /**
     * 1006 未知主机
     */
    const val UNKNOWN_HOST_ERROR = 1006
}