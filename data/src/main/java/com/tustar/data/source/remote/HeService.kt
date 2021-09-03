package com.tustar.data.source.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface HeService {

    @GET("v7/weather/now?key=$HE_WEATHER_KEY")
    suspend fun weatherNow(@Query("location") location: String): WeatherNowResponse

    @GET("v7/weather/24h?key=$HE_WEATHER_KEY")
    suspend fun weather24h(@Query("location") location: String): WeatherHoursResponse

    @GET("v7/weather/15d?key=$HE_WEATHER_KEY")
    suspend fun weather15d(@Query("location") location: String): WeatherDaysResponse

    @GET("v7/air/now?key=$HE_WEATHER_KEY")
    suspend fun airNow(@Query("location") location: String): AirNowResponse

    @GET("v7/warning/now?key=$HE_WEATHER_KEY")
    suspend fun warningNow(@Query("location") location: String): WarningNowResponse

    @GET("v7/indices/1d?key=$HE_WEATHER_KEY&type=0")
    suspend fun indices(@Query("location") location: String): IndicesResponse



    companion object {
        const val HE_WEATHER_KEY = "fe09e51549014a1d93e708a836596d89"
        const val BASE_URL = "https://devapi.heweather.net/"
    }
}