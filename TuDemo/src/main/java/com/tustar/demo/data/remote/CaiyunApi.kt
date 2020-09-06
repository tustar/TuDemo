package com.tustar.demo.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val TOKEN = "PxNxSQWrbUPcDINL"

interface CaiyunApi {

    companion object {
        const val BASE_URL = "https://api.caiyunapp.com/"
    }

    @GET("v2/place?token=${TOKEN}&lang=zh_CN")
    suspend fun searchPlace(@Query("query") query: String): PlaceResponse

    @GET("v2.5/${TOKEN}/{lng},{lat}/realtime.json")
    suspend fun getRealtime(
        @Path("lng") lng: Double,
        @Path("lat") lat: Double
    ): RealtimeResponse
}