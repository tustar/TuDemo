package com.tustar.weather.ui

import android.os.Build
import androidx.annotation.RequiresApi
import com.tustar.data.Weather
import com.tustar.data.source.remote.*
import com.tustar.weather.WeatherPrefs
import com.tustar.weather.util.DateUtils

class WeatherContact {

    data class State(
        val weather: Weather = Empty,
        val prefs: WeatherPrefs,
        val loading: Boolean = false,
    ) {
        companion object {
            /**
            "name": "景山公园",
            "id": "10101010012A",
            "lat": "39.91999",
            "lon": "116.38999",
            "adm2": "北京",
            "adm1": "北京",
            "country": "中国",
            "tz": "Asia/Shanghai",
            "utcOffset": "+08:00",
            "isDst": "0",
            "type": "scenic",
            "rank": "67",
            "fxLink": "http://hfx.link/1"
             */
            private val city = City(
                name = "__",
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

            /**
            "obsTime": "2020-06-30T21:40+08:00",
            "temp": "24",
            "feelsLike": "26",
            "icon": "101",
            "text": "多云",
            "wind360": "123",
            "windDir": "东南风",
            "windScale": "1",
            "windSpeed": "3",
            "humidity": "72",
            "precip": "0.0",
            "pressure": "1003",
            "vis": "16",
            "cloud": "10",
            "dew": "21"
             */
            private val weatherNow = WeatherNow(
                obsTime = DateUtils.obsTime(), // "2020-06-30T21:40+08:00"
                temp = "--",
                feelsLike = "__",
                icon = "101",
                text = "__",
                wind360 = "__",
                windDir = "东南风",
                windScale = "__",
                windSpeed = "3",
                humidity = "__",
                precip = "0.0",
                pressure = "1003",
                vis = "16",
                cloud = "10",
                dew = "21"
            )

            /**
            "pubTime": "2021-02-16T14:00+08:00",
            "aqi": "28",
            "level": "1",
            "category": "优",
            "primary": "NA",
            "pm10": "28",
            "pm2p5": "5",
            "no2": "3",
            "so2": "2",
            "co": "0.2",
            "o3": "76"
             */
            private val airNow = AirNow(
                pubTime = DateUtils.pubTime(), // "2021-02-16T14:00+08:00"
                aqi = "__",
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

            /**
            "fxDate": "2021-02-16",
            "aqi": "46",
            "level": "1",
            "category": "优",
            "primary": "NA"
             */
            private val air5D = mutableListOf<AirDaily>().apply {
                (0..4).forEach { days ->
                    add(
                        AirDaily(
                            fxDate = DateUtils.fxDate(days),
                            aqi = "46",
                            level = "1",
                            category = "优",
                            primary = "NA"
                        )
                    )
                }
            }

            /**
            "fxTime": "2021-02-16T15:00+08:00",
            "temp": "2",
            "icon": "100",
            "text": "晴",
            "wind360": "335",
            "windDir": "西北风",
            "windScale": "3-4",
            "windSpeed": "20",
            "humidity": "11",
            "pop": "0",
            "precip": "0.0",
            "pressure": "1025",
            "cloud": "0",
            "dew": "-25"
             */
            private val hourly24H = mutableListOf<Hourly>().apply {
                (0..23).forEach { hours ->
                    add(
                        Hourly(
                            fxTime = DateUtils.fxTime(hours), // "2021-02-16T15:00+08:00"
                            temp = "__",
                            icon = "999",
                            text = "NA",
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
                    )
                }
            }

            /**
            "fxDate": "2021-11-15",
            "sunrise": "06:58",
            "sunset": "16:59",
            "moonrise": "15:16",
            "moonset": "03:40",
            "moonPhase": "盈凸月",
            "moonPhaseIcon": "803",
            "tempMax": "12",
            "tempMin": "-1",
            "iconDay": "101",
            "textDay": "多云",
            "iconNight": "150",
            "textNight": "晴",
            "wind360Day": "45",
            "windDirDay": "东北风",
            "windScaleDay": "1-2",
            "windSpeedDay": "3",
            "wind360Night": "0",
            "windDirNight": "北风",
            "windScaleNight": "1-2",
            "windSpeedNight": "3",
            "humidity": "65",
            "precip": "0.0",
            "pressure": "1020",
            "vis": "25",
            "cloud": "4",
            "uvIndex": "3"
             */
            private val daily15D = mutableListOf<WeatherDaily>().apply {
                (0..14).forEach { days ->
                    add(
                        WeatherDaily(
                            fxDate = DateUtils.fxDate(days),
                            sunrise = "06:58",
                            sunset = "16:59",
                            moonrise = "15:16",
                            moonset = "03:40",
                            moonPhase = "盈凸月",
                            moonPhaseIcon = "803",
                            tempMax = "__",
                            tempMin = "__",
                            iconDay = "999",
                            textDay = "--",
                            iconNight = "999",
                            textNight = "--",
                            wind360Day = "45",
                            windDirDay = "风向",
                            windScaleDay = "__",
                            windSpeedDay = "3",
                            wind360Night = "0",
                            windDirNight = "风向",
                            windScaleNight = "__",
                            windSpeedNight = "3",
                            humidity = "65",
                            precip = "0.0",
                            pressure = "1020",
                            vis = "25",
                            cloud = "4",
                            uvIndex = "3"
                        )
                    )
                }
            }
            private val indices1D = arrayListOf(
                IndicesDaily(
                    date = DateUtils.date(),
                    type = 1,
                    name = "运动指数",
                    level = "3",
                    category = "NA",
                    text = "NA"
                ),
                IndicesDaily(
                    date = DateUtils.date(),
                    type = 2,
                    name = "洗车指数",
                    level = "3",
                    category = "NA",
                    text = "NA"
                ),
                IndicesDaily(
                    date = DateUtils.date(),
                    type = 3,
                    name = "穿衣指数",
                    level = "3",
                    category = "NA",
                    text = "NA"
                ),
                IndicesDaily(
                    date = DateUtils.date(),
                    type = 5,
                    name = "紫外线指数",
                    level = "3",
                    category = "NA",
                    text = "NA"
                ),
                IndicesDaily(
                    date = DateUtils.date(),
                    type = 9,
                    name = "感冒指数",
                    level = "3",
                    category = "NA",
                    text = "NA"
                ),
                IndicesDaily(
                    date = DateUtils.date(),
                    type = 15,
                    name = "交通指数",
                    level = "3",
                    category = "NA",
                    text = "NA"
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