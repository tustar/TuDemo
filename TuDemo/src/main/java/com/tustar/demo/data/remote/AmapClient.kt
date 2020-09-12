package com.tustar.demo.data.remote

object AmapClient {
    val service: AMapApi by lazy {
        RetrofitManager.create(AMapApi::class.java, AMapApi.BASE_URL)
    }
}