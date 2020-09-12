package com.tustar.demo.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

private const val AMAP_APP_KEY = "f097123e081466d050183966fc3aff39"
private const val AMAP_WEB_KEY = "08f34d7911b5ee3c6da80eff3ac1d560"

interface AMapApi {

    companion object {
        const val BASE_URL = "https://restapi.amap.com/"
    }

    @GET("v3/geocode/regeo?output=json&key=${AMAP_WEB_KEY}&radius=1000&extensions=all")
    suspend fun geocode(@Query("location") location: String): GeocodeResponse
}