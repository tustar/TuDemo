package com.tustar.data.source.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


class AirNowResponse(
    code: String,
    fxLink: String,
    refer: Refer,
    updateTime: String,
    val now: AirNow,
    val station: List<Station>,
) : HeResponse(code, fxLink, refer, updateTime)

@Parcelize
data class AirNow(
    val aqi: Int,
    val category: String,
    val co: String,
    val level: String,
    val no2: String,
    val o3: String,
    val pm10: String,
    val pm2p5: String,
    val primary: String,
    val pubTime: String,
    val so2: String
) : Parcelable

data class Station(
    val aqi: String,
    val category: String,
    val co: String,
    val id: String,
    val level: String,
    val name: String,
    val no2: String,
    val o3: String,
    val pm10: String,
    val pm2p5: String,
    val primary: String,
    val pubTime: String,
    val so2: String
)