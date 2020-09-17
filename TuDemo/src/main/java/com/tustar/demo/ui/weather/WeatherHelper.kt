package com.tustar.demo.ui.weather

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.tustar.demo.R
import com.tustar.demo.data.remote.Realtime
import com.tustar.demo.data.remote.Wind
import com.tustar.demo.databinding.FragmentWeatherBinding
import com.tustar.ktx.getStringByName

@SuppressLint("SetTextI18n")
fun Realtime.updateView(context: Context, binding: FragmentWeatherBinding) {
    binding.temperature.text = context.getString(R.string.weather_temperature, temperature)
    binding.apparentTemperature.text =
        context.getString(R.string.weather_apparent_temperature, apparentTemperature)
    binding.sky.text = context.getStringByName(skycon)
    binding.wind.text = wind.toValue(context)
    binding.humidity.text =
        context.getString(R.string.weather_humidity, (humidity * 100).toInt()) + "%"
}

fun Wind.toValue(context: Context): String {
    val resName = when {
        //        N	北	0°
        direction == 0F -> "N"
        //        NNE	东北偏北	22.5°
        direction == 22.5F || direction < 45F -> "NNE"
        //        NE	东北	45°
        direction == 45F -> "NE"
        //        ENE	东北偏东	67.5°
        direction == 67.5F || direction < 90F -> "ENE"
        //        E	东	90°
        direction == 90F -> "E"
        //        ESE	东南偏东	112.5°
        direction == 112.5F || direction < 135F -> "ESE"
        //        SE	东南	135°
        direction == 135F -> "SE"
        //        SSE	东南偏南	157.5°
        direction == 157.5F || direction < 180F -> "SSE"
        //        S	南	180°
        direction == 180F -> "S"
        //        SSW	西南偏南	202.5°
        direction == 202.5F || direction < 225F -> "SSW"
        //        SW	西南	225°
        direction == 225F -> "SW"
        //        WSW	西南偏西	247.5°
        direction == 247.5F || direction < 270F -> "WSW"
        //        W	西	270°
        direction == 270F -> "W"
        //        WNW	西北偏西	292.5°
        direction == 292.5F || direction < 315F -> "WNW"
        //        NW	西北	315°
        direction == 315F -> "NW"
        //        NNW	西北偏北	337.5°
        direction == 337.5F || direction < 360 -> "NNW"
        else -> {
            "N"
        }
    }

    val level = when {
        speed < 1 -> 0
        speed >= 1 && speed < 6 -> 1
        speed >= 6 && speed < 12 -> 2
        speed >= 12 && speed < 20 -> 3
        speed >= 20 && speed < 29 -> 4
        speed >= 29 && speed < 39 -> 5
        speed >= 39 && speed < 50 -> 6
        speed >= 50 && speed < 62 -> 7
        speed >= 62 && speed < 75 -> 8
        speed >= 75 && speed < 89 -> 9
        speed >= 89 && speed < 103 -> 10
        speed >= 103 && speed < 118 -> 11
        speed >= 118 && speed < 134 -> 12
        else -> 1
    }

    return context.getString(R.string.weather_wind, context.getStringByName(resName), level)
}

fun Location.toParams(): String = "${longitude},${latitude}"
