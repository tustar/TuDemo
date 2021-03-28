package com.tustar.demo.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface HeService {

    @GET("v7/weather/now?key=$HE_WEATHER_KEY")
    suspend fun now(@Query("location") location: String): NowResponse

    companion object {
        private const val HE_WEATHER_KEY = "fe09e51549014a1d93e708a836596d89"
        private const val BASE_URL = "https://devapi.heweather.net/"

        fun create(): HeService {
            val logger = HttpLoggingInterceptor().apply {
                level =
                    HttpLoggingInterceptor.Level.BASIC
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HeService::class.java)
        }
    }
}