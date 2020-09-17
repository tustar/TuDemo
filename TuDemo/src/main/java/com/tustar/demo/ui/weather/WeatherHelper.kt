package com.tustar.demo.ui.weather

import android.annotation.SuppressLint
import android.content.Context
import com.amap.api.location.AMapLocation
import com.tustar.demo.R
import com.tustar.demo.data.remote.Now
import com.tustar.demo.databinding.FragmentWeatherBinding
import com.tustar.ktx.getDrawableByName

@SuppressLint("SetTextI18n")
fun Now.updateView(context: Context, binding: FragmentWeatherBinding) {
    binding.temp.text = context.getString(R.string.weather_temp, temp)
    binding.feelsLike.text =
        context.getString(R.string.weather_feels_like, feelsLike)
    binding.sky.text = text
    val icon =
        context.getDrawableByName("w_$icon")
    icon?.setBounds(0, 0, icon.minimumWidth, icon.minimumHeight)
    binding.sky.setCompoundDrawables(icon, null, null, null)
    binding.wind.text = context.getString(R.string.weather_wind, windDir, windScale)
    binding.humidity.text =
        context.getString(R.string.weather_humidity, humidity) + "%"
}

@SuppressLint("SetTextI18n")
fun Now.toContent(context: Context): String {
    val temp = context.getString(R.string.weather_temp, temp)
    val feelsLike =
        context.getString(R.string.weather_feels_like, feelsLike)
    val sky = text
    val wind = context.getString(R.string.weather_wind, windDir, windScale)
    val humidity =
        context.getString(R.string.weather_humidity, humidity) + "%"
    return "$temp $feelsLike $sky $wind $humidity"
}

fun AMapLocation.toParams(): String = "${longitude},${latitude}"
