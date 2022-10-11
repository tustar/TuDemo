package com.tustar.weather.util

import android.content.Context
import android.location.Location
import com.tustar.data.source.remote.City
import com.tustar.weather.WLocation
import com.tustar.weather.WeatherPrefs

fun Location.toParams(): String = "${longitude},${latitude}"
fun WLocation.toParams(): String = if (id.isNullOrEmpty()) "$lon,$lat" else id
fun WLocation.isValid(): Boolean {
    if (id.isNotEmpty()) {
        return true
    }

    if (lon.isNotEmpty() && lat.isNotEmpty()) {
        return true
    }

    return false
}

fun WLocation.isNotValid() = !isValid()
fun WLocation.isSame(city: City): Boolean {
    return adm1 == city.adm1 && adm2 == city.adm2 && name == city.name
}

fun City.toWLocation() = WLocation.newBuilder()
    .setId(id)
    .setLat(lat)
    .setLon(lon)
    .setName(name)
    .setAdm1(adm1)
    .setAdm2(adm2)
    .build()!!





