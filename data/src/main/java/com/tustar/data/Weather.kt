package com.tustar.data

import android.os.Parcelable
import com.tustar.data.source.remote.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    val weatherNow: WeatherNow,
    val warning: List<Warning>,
    val airNow: AirNow,
    val air5d: List<AirDaily>,
    val hourly24h: List<Hourly>,
    val daily15d: List<WeatherDaily>,
    val indices: List<IndicesDaily>,
) : Parcelable