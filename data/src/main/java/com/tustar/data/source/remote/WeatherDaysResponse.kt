package com.tustar.data.source.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class WeatherDaysResponse(
    code: String,
    fxLink: String,
    refer: Refer,
    updateTime: String,
    val daily: List<WeatherDaily>,
) : Response(code, fxLink, refer, updateTime)


@Parcelize
data class WeatherDaily(
    val cloud: String,
    val fxDate: String,
    val humidity: String,
    val iconDay: String,
    val iconNight: String,
    val moonPhase: String,
    val moonrise: String,
    val moonset: String,
    val precip: String,
    val pressure: String,
    val sunrise: String,
    val sunset: String,
    val tempMax: Int,
    val tempMin: Int,
    val textDay: String,
    val textNight: String,
    val uvIndex: String,
    val vis: String,
    val wind360Day: String,
    val wind360Night: String,
    val windDirDay: String,
    val windDirNight: String,
    val windScaleDay: String,
    val windScaleNight: String,
    val windSpeedDay: String,
    val windSpeedNight: String
) : Parcelable