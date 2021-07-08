package com.tustar.demo.data.source.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface HeService {

    @GET("v7/weather/now?key=$HE_WEATHER_KEY")
    suspend fun now(@Query("location") location: String): NowResponse

    companion object {
        const val HE_WEATHER_KEY = "fe09e51549014a1d93e708a836596d89"
        const val BASE_URL = "https://devapi.heweather.net/"
    }
}