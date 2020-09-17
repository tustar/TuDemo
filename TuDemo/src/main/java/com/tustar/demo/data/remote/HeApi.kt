package com.tustar.demo.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

private const val HE_WEATHER_KEY = "fe09e51549014a1d93e708a836596d89"

interface HeApi {

    companion object {
        const val BASE_URL = "https://devapi.heweather.net/"
    }

    @GET("v7/weather/now?key=$HE_WEATHER_KEY")
    suspend fun now(@Query("location") location: String): NowResponse

    @GET("v7/weather/24h?key=$HE_WEATHER_KEY")
    suspend fun hourly(@Query("location") location: String): HourlyResponse

    @GET("v7/astronomy/sunmoon?key=$HE_WEATHER_KEY")
    suspend fun sunmoon(
        @Query("location") location: String,
        @Query("date") date: String
    ): Any

    @GET("v7/air/now?key=$HE_WEATHER_KEY")
    suspend fun airNow(@Query("location") location: String): Any

    @GET("v7/indices/1d?key=$HE_WEATHER_KEY")
    suspend fun indices(
        @Query("location") location: String,
        @Query("type") type: String
    ): Any

    @GET("v7/warning/now?key=$HE_WEATHER_KEY")
    suspend fun warning(@Query("location") location: String): Any


}