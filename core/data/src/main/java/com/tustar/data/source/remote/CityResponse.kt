package com.tustar.data.source.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class CityResponse(
    code: String,
    refer: Refer,
    val topCityList: List<City>
) : Response(code, refer = refer)

class CityLookupResponse(
    code: String,
    refer: Refer,
    val location: List<City>
) : Response(code, refer = refer)

@Parcelize
data class City(
    val adm1: String,
    val adm2: String,
    val country: String,
    val fxLink: String,
    val id: String,
    val isDst: String,
    val lat: String,
    val lon: String,
    val name: String,
    val rank: String,
    val type: String,
    val tz: String,
    val utcOffset: String
) : Parcelable