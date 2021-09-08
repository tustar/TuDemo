package com.tustar.data.source.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class AirDaysResponse(
    code: String,
    fxLink: String,
    refer: Refer,
    updateTime: String,
    val daily: List<AirDaily>,
) : HeResponse(code, fxLink, refer, updateTime)

@Parcelize
data class AirDaily(
    val aqi: Int,
    val category: String,
    val fxDate: String,
    val level: String,
    val primary: String
) : Parcelable