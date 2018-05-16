package com.tustar.retrofit2.data.bean

object MsgCode {
    /**
     * 无效的短信验证码
     */
    const val UNVALID_MSG_CODE = "unvalid_msg_code"
    /**
     * 插入失败
     */
    const val INSERT_FAIL = "insert_fail"
    /**
     * 发送短信超限
     */
    const val SEND_MSG_OVERDUE = "send_msg_overdue"

    /**
     * 发送短信失败
     */
    const val SEND_MSG_ERROR = "send_msg_error"

    /**
     * 手机号码格式不正确
     */
    const val MOBILE_FORMAT_ERROR = "mobile_format_error"
}