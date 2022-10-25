package com.tustar.data.source.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class WeatherNowResponse(
    code: String,
    fxLink: String,
    refer: Refer,
    updateTime: String,
    val now: WeatherNow,
) : Response(code, fxLink, refer, updateTime)

@Parcelize
data class WeatherNow(
    val cloud: String,
    val dew: String,
    val feelsLike: String,
    val humidity: String,
    val icon: String,
    val obsTime: String,
    val precip: String,
    val pressure: String,
    val temp: String,
    val text: String,
    val vis: String,
    val wind360: String,
    val windDir: String,
    val windScale: String,
    val windSpeed: String
) : Parcelable