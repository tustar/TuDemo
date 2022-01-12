package com.tustar.weather.util

import android.content.Context
import com.tustar.data.source.remote.City
import com.tustar.weather.Location
import com.tustar.weather.WeatherPrefs
import kotlin.reflect.KFunction2

fun Location.toParams(): String = if (id.isNullOrEmpty()) "$lon,$lat" else id
fun Location.isValid(): Boolean {
    if (id.isNotEmpty()) {
        return true
    }

    if (lon.isNotEmpty() && lat.isNotEmpty()) {
        return true
    }

    return false
}

fun Location.isNotValid() = !isValid()
fun Location.isSame(city: City): Boolean {
    return adm1 == city.adm1 && adm2 == city.adm2 && name == city.name
}

fun City.toLocation() = Location.newBuilder()
    .setId(id)
    .setLat(lat)
    .setLon(lon)
    .setName(name)
    .setAdm1(adm1)
    .setAdm2(adm2)
    .build()!!

typealias TrendSwitchMode = Pair<WeatherPrefs.Mode, KFunction2<Context, WeatherPrefs.Mode, Unit>>




