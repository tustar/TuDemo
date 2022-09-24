package com.tustar.data.source.remote

import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class BaseUrlInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val builder = request.newBuilder()
        val baseUrl = request.headers[BASE_URL]
        if (baseUrl.isNullOrEmpty()) {
            return chain.proceed(request)
        }

        builder.removeHeader(BASE_URL)
        val httpUrl = baseUrl.toHttpUrl()
        val url = request.url
            .newBuilder()
            .scheme(httpUrl.scheme)
            .host(httpUrl.host)
            .port(httpUrl.port)
            .build()
        request = builder.url(url).build()
        return chain.proceed(request)
    }

    companion object {
        const val BASE_URL = "base_url"
    }
}