package com.tustar.data.source.remote

import com.tustar.data.source.remote.BaseUrlInterceptor.Companion.BASE_URL
import retrofit2.http.*


interface HeService {

    @GET("/v7/weather/now?key=$HE_WEATHER_KEY")
    suspend fun weatherNow(@Query("location") location: String): WeatherNowResponse

    @GET("/v7/weather/24h?key=$HE_WEATHER_KEY")
    suspend fun weather24h(@Query("location") location: String): WeatherHoursResponse

    @GET("/v7/weather/15d?key=$HE_WEATHER_KEY")
    suspend fun weather15d(@Query("location") location: String): WeatherDaysResponse

    @GET("/v7/air/now?key=$HE_WEATHER_KEY")
    suspend fun airNow(@Query("location") location: String): AirNowResponse

    @GET("/v7/air/5d?key=$HE_WEATHER_KEY")
    suspend fun air5d(@Query("location") location: String): AirDaysResponse

    @GET("/v7/warning/now?key=$HE_WEATHER_KEY")
    suspend fun warningNow(@Query("location") location: String): WarningNowResponse

    /**
     * 名称	TYPE	级别名称(对应等级数值)
    全部生活指数	0
    运动指数	1	适宜(1)、较适宜(2)、较不宜(3)
    洗车指数	2	适宜(1)、较适宜(2)、较不宜(3)、不宜(4)
    穿衣指数	3	寒冷(1)、冷(2)、较冷(3)、较舒适(4)、舒适(5)、热(6)、炎热(7)
    钓鱼指数	4	适宜(1)、较适宜(2)、不宜(3)
    紫外线指数	5	最弱(1)、弱(2)、中等(3)、强(4)、很强(5)
    旅游指数	6	适宜(1)、较适宜(2)、一般(3)、较不宜(4)、不适宜(5)
    花粉过敏指数	7	极不易发(1)、不易发(2)、较易发(3)、易发(4)、极易发(5)
    舒适度指数	8	舒适(1)、较舒适(2)、较不舒适(3)、很不舒适(4)、极不舒适(5)、不舒适(6)、非常不舒适(7)
    感冒指数	9	少发(1)、较易发(2)、易发(3)、极易发(4)
    空气污染扩散条件指数	10	优(1)、良(2)、中(3)、较差(4)、很差(5)
    空调开启指数	11	长时间开启(1)、部分时间开启(2)、较少开启(3)、开启制暖空调(4)
    太阳镜指数	12	不需要(1)、需要(2)、必要(3)、很必要(4)、非常必要(5)
    化妆指数	13	保湿(1)、保湿防晒(2)、去油防晒(3)、防脱水防晒(4)、去油(5)、防脱水(6)、防晒(7)、滋润保湿(8)
    晾晒指数	14	极适宜(1)、适宜(2)、基本适宜(3)、不太适宜(4)、不宜(5)、不适宜(6)
    交通指数	15	良好(1)、较好(2)、一般(3)、较差(4)、很差(5)
    防晒指数	16	弱(1)、较弱(2)、中等(3)、强(4)、极强(5)
     */
    @GET("/v7/indices/1d?key=$HE_WEATHER_KEY&type=1,2,3,5,9,15")
    suspend fun indices(@Query("location") location: String): IndicesResponse

    @GET("/v2/city/top?key=$HE_WEATHER_KEY")
    @Headers("$BASE_URL:$GEO_BASE_URL")
    suspend fun cityTop(
        @Query("number") number: Int = 20,
        @Query("range") range: String = "cn"
    ): CityTopResponse

    @GET("/v2/city/lookup?key=$HE_WEATHER_KEY")
    @Headers("$BASE_URL:$GEO_BASE_URL")
    suspend fun cityLookup(
        @Query("location") location: String,
    ): LookupResponse

    companion object {
        const val HE_WEATHER_KEY = "fe09e51549014a1d93e708a836596d89"
        const val HE_BASE_URL = "https://devapi.heweather.net"
        const val GEO_BASE_URL = "https://geoapi.qweather.com"
    }
}