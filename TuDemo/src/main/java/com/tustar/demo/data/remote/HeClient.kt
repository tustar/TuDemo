package com.tustar.demo.data.remote

object HeClient {
    val service: HeApi by lazy {
        RetrofitManager.create(HeApi::class.java, HeApi.BASE_URL)
    }
}