package com.tustar.data.source.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class WeatherHoursResponse(
    code: String,
    fxLink: String,
    refer: Refer,
    updateTime: String,
    val hourly: List<Hourly>,
) : Response(code, fxLink, refer, updateTime)

@Parcelize
data class Hourly(
    val cloud: String,
    val dew: String,
    val fxTime: String,
    val humidity: String,
    val icon: Int,
    val pop: String,
    val precip: String,
    val pressure: String,
    val temp: Int,
    val text: String,
    val wind360: String,
    val windDir: String,
    val windScale: String,
    val windSpeed: String
) : Parcelable