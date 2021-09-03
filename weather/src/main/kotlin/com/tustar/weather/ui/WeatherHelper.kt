package com.tustar.weather.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import com.tustar.data.source.remote.WeatherDaily
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

object WeatherHelper {
    fun aqiColor(aqi: Int) = when (aqi) {
        in 0..50 -> Color(0XFF73DC29)
        in 51..100 -> Color(0XFFEADD14)
        in 101..150 -> Color(0XFFEA8A01)
        in 151..200 -> Color(0XFFFF460D)
        in 201..300 -> Color(0XFFB12C9A)
        else -> Color(0XFF582C21)
    }

    fun iconId(context: Context, daily: WeatherDaily): Int {
        val icon = if (isDay()) daily.iconDay else daily.iconNight
        return context.resources.getIdentifier(
            "ic_${icon}", "mipmap", context.packageName
        )
    }

    fun dailyText(daily: WeatherDaily): String = if (isDay()) daily.textDay else daily.textNight

    private fun isDay() = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) in 6..17

    fun iconId(context: Context, icon: Int): Int {
        return context.resources.getIdentifier(
            "ic_${icon}", "mipmap", context.packageName
        )
    }

    /**
     * 2021-02-16T15:00+08:00 => 15:00
     */
    @SuppressLint("NewApi")
    fun hourlyTime(fxTime: String): Pair<Boolean, String> {
        val timeInMillis = SimpleDateFormat("yyyy-MM-dd'T'HH:mmXXX").parse(fxTime).time
        val fxTimeHour = Calendar.getInstance().run {
            this.timeInMillis = timeInMillis
            get(Calendar.HOUR_OF_DAY)
        }
        val currHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val isNow = fxTimeHour == currHour
        //
        val formatFxTime = SimpleDateFormat("HH:mm").format(timeInMillis)
        return Pair(isNow, formatFxTime)
    }

    /**
     * 2021-09-03 =>  09/03 周五
     */
    fun dateWeek(fxDate: String): Triple<String, String, Boolean> {
        val timeInMillis = SimpleDateFormat("yyyy-MM-dd").parse(fxDate).time
        val date = SimpleDateFormat("MM/dd").format(timeInMillis)
        val week = SimpleDateFormat("W").format(timeInMillis)
        val isToday = SimpleDateFormat("yyyy-MM-dd").format(Date()) == fxDate
        return Triple(date, week, isToday)
    }
}