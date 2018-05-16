package com.tustar.common.util

import java.util.regex.Pattern

object MobileUtils {

    // 手机号校验
    private const val REGEX_MOBILE_EXACT =
            "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(16[6])|(17[0,1,3,5-8])|(18[0-9])|(19[8,9]))\\d{8}$"

    // 判断输入的手机号是否合法
    @JvmStatic
    fun isMobileOk(mobile: String?): Boolean {
        return mobile != null && mobile.isNotEmpty() && Pattern.matches(REGEX_MOBILE_EXACT, mobile)
    }
}