package com.tustar.retrofit2.util

import com.tustar.retrofit2.BuildConfig

object NetUtils {

    const val PARTNER = "com.tustar.demo"
    const val UTF_8 = "utf-8"
    const val SIGN = "sign"

    private fun getBasicParams(): MutableMap<String, String> {
        val params = mutableMapOf<String, String>()
        params["partner"] = PARTNER
        val timestamp = (System.currentTimeMillis() / 1000).toString()
        params["timestamp"] = timestamp
        return params
    }

    fun getSignedParams(params: MutableMap<String, String> = mutableMapOf()):
            MutableMap<String, String> {
        params.putAll(getBasicParams())
        val signStr = RsaUtils.buildSignContent(params)
        val sign = RsaUtils.rsaSign(signStr, BuildConfig.PRIVATE_KEY, UTF_8)
        params[SIGN] = sign
        return params
    }

}