package com.tustar.weather.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    // "2020-06-30T21:40+08:00"
    fun obsTime(): String {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mmXXX", Locale.CHINA)
            .format(Date())
    }

    // "2021-02-16T14:00+08:00"
    fun pubTime(): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MINUTE, 0)
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mmXXX", Locale.CHINA)
            .format(Date(calendar.timeInMillis))
    }

    // "2021-02-16"
    fun fxDate(days: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, days)
        return SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
            .format(Date(calendar.timeInMillis))
    }

    // "2021-02-16T15:00+08:00"
    fun fxTime(hours: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR_OF_DAY, hours)
        calendar.set(Calendar.MINUTE, 0)
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mmXXX", Locale.CHINA)
            .format(Date(calendar.timeInMillis))
    }

    // "2021-02-16"
    fun date(): String {
        val calendar = Calendar.getInstance()
        return SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
            .format(Date(calendar.timeInMillis))
    }
}