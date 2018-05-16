package com.tustar.retrofit2.util

import java.util.regex.Pattern

object CodeUtils {
    // 验证码校验
    val REGEX_CODE = "^\\d{6}$"

    @JvmStatic
    fun isCodeOk(code: String?): Boolean {
        return code != null && code.isNotEmpty() && Pattern.matches(REGEX_CODE, code)
    }
}