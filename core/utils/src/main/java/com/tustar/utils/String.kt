package com.tustar.utils

import java.util.regex.Pattern

const val REGEX_MOBILE_EXACT =
        "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(16[6])|(17[0,1,3,5-8])|(18[0-9])|(19[8,9]))\\d{8}$"

fun String.isMobile(): Boolean =
        this.isNotEmpty() && Pattern.matches(REGEX_MOBILE_EXACT, this)