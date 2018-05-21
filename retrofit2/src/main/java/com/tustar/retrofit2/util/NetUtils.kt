package com.tustar.retrofit2.util

import android.content.Context
import com.tustar.common.util.Logger
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

    fun getSignedParams(context: Context, params: MutableMap<String, String> = mutableMapOf()):
            MutableMap<String, String> {
        params.putAll(getBasicParams())
        val signStr = RsaUtils.buildSignContent(params)
        val privateKey = RsaUtils.getPrivateKey(context, BuildConfig.PRIVATE_KEY)
        val sign = RsaUtils.rsaSign(signStr, privateKey, UTF_8)
        params[SIGN] = sign ?: ""
        return params
    }

}