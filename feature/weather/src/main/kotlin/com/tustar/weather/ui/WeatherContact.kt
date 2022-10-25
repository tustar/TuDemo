package com.tustar.weather.ui

import com.tustar.data.Weather
import com.tustar.data.source.remote.*
import com.tustar.weather.WeatherPrefs
import java.util.Collections

class WeatherContact {

    data class State(
        val weather: Weather = Empty,
        val prefs: WeatherPrefs,
        val loading: Boolean = false,
    ) {
        companion object {
            private val city = City(
                name = "景山公园",
                id = "10101010012A",
                lat = "39.91999",
                lon = "116.38999",
                adm2 = "北京",
                adm1 = "北京",
                country = "中国",
                tz = "Asia/Shanghai",
                utcOffset = "+08:00",
                isDst = "0",
                type = "scenic",
                rank = "67",
                fxLink = "http://hfx.link/1"
            )
            private val weatherNow = WeatherNow(
                obsTime = "2020-06-30T21:40+08:00",
                temp = 24,
                feelsLike = 26,
                icon = "101",
                text = "多云",
                wind360 = 123,
                windDir = "东南风",
                windScale = 1,
                windSpeed = 3.0f,
                humidity = 72,
                precip = "0.0",
                pressure = 1003,
                vis = "16",
                cloud = 10,
                dew = "21"
            )
            private val airNow = AirNow(
                pubTime = "2021-02-16T14:00+08:00",
                aqi = 28,
                level = "1",
                category = "优",
                primary = "NA",
                pm10 = "28",
                pm2p5 = "5",
                no2 = "3",
                so2 = "2",
                co = "0.2",
                o3 = "76"
            )
            private val airDaily = AirDaily(
                fxDate = "2021-02-16",
                aqi = 46,
                level = "1",
                category = "优",
                primary = "NA"
            )
            private val air5D = mutableListOf<AirDaily>().apply {
                repeat(5) {
                    add(airDaily)
                }
            }
            private val hourly = Hourly(
                fxTime = "2021-02-16T15:00+08:00",
                temp = 2,
                icon = "100",
                text = "晴",
                wind360 = "335",
                windDir = "西北风",
                windScale = "3-4",
                windSpeed = "20",
                humidity = "11",
                pop = "0",
                precip = "0.0",
                pressure = "1025",
                cloud = "0",
                dew = "-25"
            )
            private val hourly24H = mutableListOf<Hourly>().apply {
                repeat(24) {
                    add(hourly)
                }
            }
            private val weatherDaily = WeatherDaily(
                fxDate = "2021-11-15",
                sunrise = "--:--",
                sunset = "--:--",
                moonrise = "--:--",
                moonset = "--:--",
                moonPhase = "盈凸月",
                moonPhaseIcon = "803",
                tempMax = 12,
                tempMin = -1,
                iconDay = "101",
                textDay = "多云",
                iconNight = "150",
                textNight = "晴",
                wind360Day = "45",
                windDirDay = "东北风",
                windScaleDay = "1-2",
                windSpeedDay = "3",
                wind360Night = "0",
                windDirNight = "北风",
                windScaleNight = "1-2",
                windSpeedNight = "3",
                humidity = "65",
                precip = "0.0",
                pressure = "1020",
                vis = "25",
                cloud = "4",
                uvIndex = "3"
            )
            private val daily15D = mutableListOf<WeatherDaily>().apply {
                repeat(15) {
                    add(weatherDaily)
                }
            }
            private val indices1D = arrayListOf(
                IndicesDaily(
                    date = "2021-12-16",
                    type = 1,
                    name = "运动指数",
                    level = "3",
                    category = "--",
                    text = "--"
                ),
                IndicesDaily(
                    date = "2021-12-16",
                    type = 2,
                    name = "洗车指数",
                    level = "3",
                    category = "--",
                    text = "--"
                ),
                IndicesDaily(
                    date = "2021-12-16",
                    type = 3,
                    name = "穿衣指数",
                    level = "3",
                    category = "--",
                    text = "--"
                ),
                IndicesDaily(
                    date = "2021-12-16",
                    type = 5,
                    name = "紫外线指数",
                    level = "3",
                    category = "--",
                    text = "--"
                ),
                IndicesDaily(
                    date = "2021-12-16",
                    type = 9,
                    name = "感冒指数",
                    level = "3",
                    category = "--",
                    text = "--"
                ),
                IndicesDaily(
                    date = "2021-12-16",
                    type = 15,
                    name = "交通指数",
                    level = "3",
                    category = "--",
                    text = "--"
                ),
            )
            val Empty = Weather(
                city = city,
                weatherNow = weatherNow,
                warning = emptyList(),
                airNow = airNow,
                air5D = air5D,
                hourly24H = hourly24H,
                daily15D = daily15D,
                indices1D = indices1D,
            )
        }
    }

    sealed class Effect {
        object DataWasLoaded : Effect()
    }
}