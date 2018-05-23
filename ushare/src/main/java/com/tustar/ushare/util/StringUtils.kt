package com.tustar.ushare.util

object StringUtils {
    /**
     * 判断字符串空指针或者内容为空
     *
     * @param source
     * @return
     */
    @JvmStatic
    fun isEmpty(source: String?): Boolean {
        return source == null || source == ""
    }
}