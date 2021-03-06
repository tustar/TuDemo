package com.tustar.demo.data.source.remote

data class NowResponse(
    val code: String,
    val fxLink: String,
    val now: Now,
    val refer: Refer,
    val updateTime: String
)

data class Now(
    val cloud: Int,
    val dew: String,
    val feelsLike: Int,
    val humidity: Int,
    val icon: String,
    val obsTime: String,
    val precip: String,
    val pressure: String,
    val temp: Int,
    val text: String,
    val vis: String,
    val wind360: Int,
    val windDir: String,
    val windScale: Int,
    val windSpeed: Float
)