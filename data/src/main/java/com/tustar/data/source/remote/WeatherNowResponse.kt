package com.tustar.data.source.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class WeatherNowResponse(
    code: String,
    fxLink: String,
    refer: Refer,
    updateTime: String,
    val now: WeatherNow,
) : HeResponse(code, fxLink, refer, updateTime)

@Parcelize
data class WeatherNow(
    val cloud: Int,
    val dew: String,
    val feelsLike: Int,
    val humidity: Int,
    val icon: String,
    val obsTime: String,
    val precip: String,
    val pressure: Int,
    val temp: Int,
    val text: String,
    val vis: String,
    val wind360: Int,
    val windDir: String,
    val windScale: Int,
    val windSpeed: Float
) : Parcelable