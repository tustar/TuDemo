package com.tustar.demo.data.remote

object CaiyunClient {
    val service: CaiyunApi by lazy {
        RetrofitManager.create(CaiyunApi::class.java, CaiyunApi.BASE_URL)
    }
}