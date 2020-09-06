package com.tustar.demo.data.remote

import com.google.gson.annotations.SerializedName

data class Wind(
    val speed: Float,
    val direction: Float
)

data class Local(
    val status: String,
    val datasource: String,
    val intensity: Int
)

data class Nearest(
    val status: String,
    val distance: Float,
    val intensity: Float
)

data class Precipitation(
    val local: Local,
    val nearest: Nearest
)

data class Aqi(
    val chn: Int,
    val usa: Int
)

data class Description(
    val usa: String,
    val chn: String
)

data class AirQuality(
    val pm25: Float,
    val pm10: Float,
    val o3: Float,
    val so2: Float,
    val no2: Float,
    val co: Float,
    val aqi: Aqi,
    val description: Description
)

data class Ultraviolet(
    val index: Int,
    val desc: String
)

data class Comfort(
    val index: Int,
    val desc: String
)

data class LifeIndex(
    val ultraviolet: Ultraviolet,
    val comfort: Comfort
)

data class Realtime(
    val status: String,
    val temperature: Float,
    val humidity: Float,
    val cloudrate: Float,
    val skycon: String,
    val visibility: Float,
    val dswrf: Float,
    val wind: Wind,
    val pressure: Float,
    @SerializedName("apparent_temperature")
    val apparentTemperature: Float,
    val precipitation: Precipitation,
    @SerializedName("air_quality")
    val airQuality: AirQuality,
    @SerializedName("life_index")
    val lifeIndex: LifeIndex
)

data class Result(
    val realtime: Realtime,
    val primary: Int
)

data class RealtimeResponse(
    val status: String,
    val api_version: String,
    val api_status: String,
    val lang: String,
    val unit: String,
    val tzshift: Long,
    val timezone: String,
    @SerializedName("server_time")
    val serverTime: Long,
    val location: List<Double>,
    val result: Result
)