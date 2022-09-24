package com.tustar.data

import android.os.Parcelable
import com.tustar.data.source.remote.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    val weatherNow: WeatherNow,
    val warning: List<Warning>,
    val airNow: AirNow,
    val air5D: List<AirDaily>,
    val hourly24H: List<Hourly>,
    val daily15D: List<WeatherDaily>,
    val indices1D: List<IndicesDaily>,
) : Parcelable