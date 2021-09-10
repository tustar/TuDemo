package com.tustar.weather.ui

import android.content.Context
import com.tustar.weather.R

object WeatherIcons {
    /**
    --预警等级--
    Level	LevelId	    备注
    白色	    5	        最低等级，仅限广东省
    蓝色	    4	        最低等级
    黄色	    3
    橙色	    2
    红色	    1	        最高等级
     */
    private fun alertLevel(level: String) = when (level) {
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
    private fun alertType(type: String) = when (type) {
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

    fun alertIconId(context: Context, type: String, level: String): Int {
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

    fun lifeIconId(context: Context, type: Int): Int {
        return context.resources.getIdentifier(
            "ic_life_${type}", "drawable", context.packageName
        )
    }
}