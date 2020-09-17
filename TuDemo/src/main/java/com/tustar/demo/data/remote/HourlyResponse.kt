package com.tustar.demo.data.remote

data class HourlyResponse(
    val code: String,
    val fxLink: String,
    val hourly: List<Hourly>,
    val refer: Refer,
    val updateTime: String
)

data class Hourly(
    val cloud: Int,
    val dew: String,
    val fxTime: String,
    val humidity: String,
    val icon: String,
    val pop: String,
    val precip: String,
    val pressure: String,
    val temp: Int,
    val text: String,
    val wind360: Int,
    val windDir: String,
    val windScale: String,
    val windSpeed: Float
)