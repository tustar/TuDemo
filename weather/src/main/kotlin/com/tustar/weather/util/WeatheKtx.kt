package com.tustar.weather.util

import com.tustar.weather.Location

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

