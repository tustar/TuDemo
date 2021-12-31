package com.tustar.weather.ui

import android.content.Context
import com.tustar.weather.ui.WeatherHelper.W_Black
import com.tustar.weather.ui.WeatherHelper.W_Blue
import com.tustar.weather.ui.WeatherHelper.W_Green
import com.tustar.weather.ui.WeatherHelper.W_Orange
import com.tustar.weather.ui.WeatherHelper.W_Red
import com.tustar.weather.ui.WeatherHelper.W_White
import com.tustar.weather.ui.WeatherHelper.W_Yellow

object WeatherIcons {

    fun alertLevel(level: String) = when (level) {
        "白色" -> W_White
        "蓝色" -> W_Blue
        "绿色" -> W_Green
        "黄色" -> W_Yellow
        "橙色" -> W_Orange
        "红色" -> W_Red
        "黑色" -> W_Black
        else -> W_Blue
    }


    fun alertIconId(context: Context, type: String): Int {
        return context.resources.getIdentifier(
            "ic_${type}", "drawable", context.packageName
        )
    }

    fun weatherIconId(context: Context, icon: String): Int {
        return context.resources.getIdentifier(
            "ic_${icon}", "drawable", context.packageName
        )
    }

    fun lifeIconId(context: Context, type: Int): Int {
        return context.resources.getIdentifier(
            "ic_life_${type}", "drawable", context.packageName
        )
    }
}