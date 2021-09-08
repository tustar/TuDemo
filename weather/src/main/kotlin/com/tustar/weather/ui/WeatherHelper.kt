package com.tustar.weather.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.ui.graphics.Color
import com.tustar.data.source.remote.WeatherDaily
import com.tustar.weather.R
import com.tustar.weather.utils.Lunar
import java.text.SimpleDateFormat
import java.util.*

object WeatherHelper {
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
    private val wGreen = Color(0xFF12C619)
    private val wRed = Color(0XFFEA5623)
    private val wBlue = Color(0XFF779CF9)
    private val wYellow = Color(0XFFFFD800)
    private val wOrange = Color(0XFFFE9900)
    private val wWhite = Color(0XFFFFFFFF)
    private val wPurple = Color(0XFFB12C9A)
    private val wBrown = Color(0XFF582C21)

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
        in 0..50 -> wGreen
        // 二级	良	黄色
        in 51..100 -> wYellow
        // 三级	轻度污染	橙色
        in 101..150 -> wOrange
        // 四级	中度污染	红色
        in 151..200 -> wRed
        // 五级	重度污染	紫色
        in 201..300 -> wPurple
        // 六级	严重污染	褐红色
        else -> wBrown
    }

    /**
    --预警等级--
    Level	LevelId	    备注
    白色	    5	        最低等级，仅限广东省
    蓝色	    4	        最低等级
    黄色	    3
    橙色	    2
    红色	    1	        最高等级

     */
    fun alertLevel(level: String) = when (level) {
        // 红色	1	最高等级
        "红色" -> "04"
        // 橙色	2
        "橙色" -> "03"
        // 黄色	3
        "黄色" -> "02"
        // 蓝色	4	最低等级
        "蓝色" -> "01"
        // 白色	5	最低等级，仅限广东省
        else -> "01"
    }

    /**
     * Type	TypeName
    11B01	台风 Y
    11B02	龙卷风
    11B03	暴雨 Y
    11B04	暴雪 Y
    11B05	寒潮 Y
    11B06	大风 Y
    11B07	沙尘暴 Y
    11B08	低温冻害 Y
    11B09	高温 Y
    11B10	热浪
    11B11	干热风
    11B12	下击暴流
    11B13	雪崩
    11B14	雷电 Y
    11B15	冰雹 Y
    11B16	霜冻 Y
    11B17	大雾 Y
    11B18	低空风切变
    11B19	霾 Y
    11B20	雷雨大风
    11B21	道路结冰 Y
    11B22	干旱
    11B23	海上大风 Y
    11B24	高温中暑 Y
    11B25	森林火险
    11B26	草原火险
    11B27	冰冻 Y
    11B28	空间天气
    11B29	重污染
    11B30	低温雨雪冰冻
    11B31	强对流
    11B32	臭氧
    11B33	大雪
    11B34	寒冷
    11B35	连阴雨
    11B36	渍涝风险
    11B37	地质灾害气象风险
    11B38	强降雨 Y
    11B39	强降温 Y
    11B40	雪灾
    11B41	森林（草原）火险
    11B42	医疗气象
    11B43	雷暴 Y
    11B44	停课信号
    11B45	停工信号
    11B46	海上风险
    11B47	春季沙尘天气 Y
    11B48	降温　Y
    11B49	台风暴雨 Y
    11B50	严寒
    11B51	沙尘 Y
    11B52	海上雷雨大风
    11B53	海上大雾 Y
    11B54	海上雷电 Y
    11B55	海上台风 Y
    11B56	低温 Y
    11B57	道路冰雪 Y
    11A01	洪水
    11B101	大风 Y
    11B102	强降雪和结冰
    11B103	大雾 Y
    11E101	海岸风险
    11B104	森林火险
    11B105	雨 Y
    11A106	大雨洪水 Y
     */
    fun alertType(type: String) = when (type) {
        // 台风, 海上台风, 台风暴雨
        "11B01", "11B55", "11B49" -> "01"
        // 暴雨, 雨, 大雨洪水, 强降雨
        "11B03", "11B105", "11A106", "11B38" -> "02"
        // 暴雪, 大雪
        "11B04", "11B33" -> "03"
        // 寒潮, 强降温, 降温
        "11B05", "11B39", "11B48" -> "04"
        // 大风, 海上大风, 大风
        "11B06", "11B23", "11B101" -> "05"
        // 春季沙尘天气, 沙尘
        "11B47", "11B51" -> "06"
        // 高温, 高温中暑
        "11B09", "11B24" -> "07"
        // 雷电, 雷暴, 海上雷电
        "11B14", "11B43", "11B54" -> "09"
        // 冰雹
        "11B15" -> "10"
        // 低温冻害, 霜冻, 冰冻, 低温
        "11B08", "11B16", "11B27", "11B56" -> "11"
        // 大雾, 大雾, 海上大雾
        "11B17", "11B103", "11B53" -> "12"
        // 霾
        "11B19" -> "13"
        // 道路结冰,道路冰雪
        "11B21", "11B57" -> "14"
        else -> -1
    }

    fun alertIcon(context: Context, type: String, level: String): Int {
        val alertType = alertType(type = type)
        if (alertType == -1) {
            return R.drawable.alert_flg_img
        }
        val alertLevel = alertLevel(level = level)
        return context.resources.getIdentifier(
            "alert_${alertType}_${alertLevel}", "drawable", context.packageName
        )
    }

    fun weatherIconId(context: Context, icon: Int): Int {
        return context.resources.getIdentifier(
            "ic_${icon}", "drawable", context.packageName
        )
    }

    fun dailyText(context: Context, weatherDaily: WeatherDaily): String {
        if (weatherDaily.textDay == weatherDaily.textNight) {
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
    fun dateWeek(context: Context, fxDate: String): Triple<String, String, Boolean> {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val isToday = format.format(Date()) == fxDate
        val timeInMillis = format.parse(fxDate).time
        val date = if (isToday) {
            context.resources.getString(R.string.weather_today)
        } else {
            SimpleDateFormat("MM/dd").format(timeInMillis)
        }
        val week = getWeek(context, timeInMillis)
        return Triple(date, week, isToday)
    }

    fun gregorianAndLunar(): String {
        val md = SimpleDateFormat("M月d日").format(Date())
        val lmd = Lunar().toMonthDay()
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
            Calendar.SUNDAY -> context.resources.getString(R.string.week_day_sunday)
            Calendar.MONDAY -> context.resources.getString(R.string.week_day_monday)
            Calendar.TUESDAY -> context.resources.getString(R.string.week_day_tuesday)
            Calendar.WEDNESDAY -> context.resources.getString(R.string.week_day_wednesday)
            Calendar.THURSDAY -> context.resources.getString(R.string.week_day_thursday)
            Calendar.FRIDAY -> context.resources.getString(R.string.week_day_friday)
            Calendar.SATURDAY -> context.resources.getString(R.string.week_day_saturday)
            else -> ""
        }
    }


}