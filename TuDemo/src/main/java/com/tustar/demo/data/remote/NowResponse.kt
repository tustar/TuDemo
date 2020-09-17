package com.tustar.demo.data.remote

data class NowResponse(
    val code: String,
    val fxLink: String,
    val now: Now,
    val refer: Refer,
    val updateTime: String
)

data class Now(
    val cloud: String,
    val dew: String,
    val feelsLike: Float,
    val humidity: Int,
    val icon: String,
    val obsTime: String,
    val precip: String,
    val pressure: String,
    val temp: Float,
    val text: String,
    val vis: String,
    val wind360: String,
    val windDir: String,
    val windScale: Int,
    val windSpeed: String
)

data class Refer(
    val license: List<String>,
    val sources: List<String>
)