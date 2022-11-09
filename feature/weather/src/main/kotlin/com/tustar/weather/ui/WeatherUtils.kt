package com.tustar.weather.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.ui.graphics.Color
import com.tustar.data.source.remote.WeatherDaily
import com.tustar.weather.R
import com.tustar.weather.util.Lunar
import java.text.SimpleDateFormat
import java.util.*

object WeatherUtils {
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
            "weather_ic_${type}", "drawable", context.packageName
        )
    }

    fun weatherIconId(context: Context, icon: String): Int {
        return context.resources.getIdentifier(
            "weather_ic_${icon}", "drawable", context.packageName
        )
    }

    fun lifeIconId(context: Context, type: Int): Int {
        return context.resources.getIdentifier(
            "weather_ic_life_${type}", "drawable", context.packageName
        )
    }
    /**
    透明度      十六进制
    100%        FF
    80%	        CC
    60%	        99
    50%	        80
    40%	        66
    10%	        1A
    20%         33
     */
    val W_Green = Color(0xFF12C619)
    val W_Red = Color(0xFFEA5623)
    val W_Blue = Color(0xFF779CF9)
    val W_Yellow = Color(0xFFCFB003)
    val W_Orange = Color(0xFFFE9900)
    val W_White = Color(0xFFFFFFFF)
    val W_Purple = Color(0xFFB12C9A)
    val W_Brown = Color(0xFF582C21)
    val W_Black = Color(0xFF000000)

    /**
    --空气质量指数等级--
    数值	    等级	    级别	    级别颜色
    0-50	一级	    优	    绿色
    51-100	二级	    良	    黄色
    101-150	三级	    轻度污染	橙色
    151-200	四级	    中度污染	红色
    201-300	五级	    重度污染	紫色
    >300	六级	    严重污染	褐红色
     */
    fun aqiColor(aqi: Int) = when (aqi) {
        // 一级	优	绿色
        in 0..50 -> W_Green
        // 二级	良	黄色
        in 51..100 -> W_Yellow
        // 三级	轻度污染	橙色
        in 101..150 -> W_Orange
        // 四级	中度污染	红色
        in 151..200 -> W_Red
        // 五级	重度污染	紫色
        in 201..300 -> W_Purple
        // 六级	严重污染	褐红色
        else -> W_Brown
    }

    fun dailyText(context: Context, weatherDaily: WeatherDaily): String {
        if (weatherDaily.textDay != "--" && weatherDaily.textDay == weatherDaily.textNight) {
            return weatherDaily.textDay
        }

        return context.resources.getString(
            R.string.weather_day_to_night,
            weatherDaily.textDay,
            weatherDaily.textNight
        )
    }

    private fun isDay() = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) in 6..17

    /**
     * 2021-02-16T15:00+08:00 => 15:00
     */
    @SuppressLint("NewApi", "SimpleDateFormat")
    fun hourlyTime(context: Context, fxTime: String): Pair<Boolean, String> {
        val prefix = SimpleDateFormat("yyyy-MM-dd'T'HH").format(Date())
        val isNow = fxTime.startsWith(prefix)
        //
        val formatFxTime = if (isNow) {
            context.resources.getString(R.string.weather_now)
        } else {
            val timeInMillis = SimpleDateFormat("yyyy-MM-dd'T'HH:mmXXX").parse(fxTime).time
            SimpleDateFormat("HH:mm").format(timeInMillis)
        }

        return Pair(isNow, formatFxTime)
    }

    /**
     * 2021-09-03 =>  09/03 周五
     */
    fun dateWeek(
        context: Context,
        fxDate: String,
        isList: Boolean = true
    ): Triple<String, String, Boolean> {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val isToday = format.format(Date()) == fxDate
        val timeInMillis = format.parse(fxDate).time
        val date = if (isToday && isList) {
            context.resources.getString(R.string.weather_today)
        } else {
            SimpleDateFormat("MM/dd").format(timeInMillis)
        }
        val week = if (isToday && !isList) {
            context.resources.getString(R.string.weather_today)
        } else {
            getWeek(context, timeInMillis)
        }

        return Triple(date, week, isToday)
    }

    fun gregorianAndLunar(context: Context): String {
        val md = SimpleDateFormat("M月d日").format(Date())
        val lmd = context.resources.getString(R.string.weather_lunar, Lunar().toMonthDay())
        return "$md $lmd"
    }

    private fun getWeek(context: Context, timeInMillis: Long): String {
        val calendar = Calendar.getInstance().apply {
            this.timeInMillis = timeInMillis
        }
        return getWeek(context, calendar)
    }

    fun getWeek(context: Context, calendar: Calendar = Calendar.getInstance()): String {
        return when (calendar[Calendar.DAY_OF_WEEK]) {
            Calendar.SUNDAY -> context.resources.getString(R.string.weather_sunday)
            Calendar.MONDAY -> context.resources.getString(R.string.weather_monday)
            Calendar.TUESDAY -> context.resources.getString(R.string.weather_tuesday)
            Calendar.WEDNESDAY -> context.resources.getString(R.string.weather_wednesday)
            Calendar.THURSDAY -> context.resources.getString(R.string.weather_thursday)
            Calendar.FRIDAY -> context.resources.getString(R.string.weather_friday)
            Calendar.SATURDAY -> context.resources.getString(R.string.weather_saturday)
            else -> ""
        }
    }

    fun calculatePercent(startTime: String, endTime: String): Float {
        val (startH, startM) = parseHHMM(startTime)
        val start = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, startH)
            set(Calendar.MINUTE, startM)
        }
        //
        val (endH, endM) = parseHHMM(endTime)
        val end = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, endH)
            set(Calendar.MINUTE, endM)
        }
        //
        val now = Calendar.getInstance()
        if (now.before(start)) {
            return 0.0f
        }
        if (now.after(end)) {
            return 1.0f
        }
        val visibleTime = end.timeInMillis - start.timeInMillis
        val passedTime = now.timeInMillis - start.timeInMillis
        return passedTime / visibleTime.toFloat()
    }

    private fun parseHHMM(time: String): Pair<Int, Int> {
        val hhMM = time.split(":")
        return Pair(hhMM[0].toInt(), hhMM[1].toInt())
    }
}