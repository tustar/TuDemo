package com.tustar.weather.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.tustar.common.Result
import com.tustar.common.asResult
import com.tustar.data.Weather
import com.tustar.data.source.WeatherRepository
import com.tustar.utils.Logger
import com.tustar.weather.WeatherPrefs
import com.tustar.weather.util.updateMode15D
import com.tustar.weather.util.updateMode24H
import com.tustar.weather.util.weatherPrefsFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    private var _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    @SuppressLint("MissingPermission")
    fun getLocation(context: Context) {
        Logger.d("getLocation")
        // 声明LocationClient类
        val locationClient = LocationClient(context.applicationContext)
        // 注册监听函数
        locationClient.registerLocationListener(object : BDAbstractLocationListener() {
            override fun onReceiveLocation(location: BDLocation) {
                // 此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
                // 以下只列举部分获取经纬度相关（常用）的结果信息
                // 更多结果信息获取说明，请参照类参考中BDLocation类中的说明
                val latitude = location.latitude //获取纬度信息
                val longitude = location.longitude //获取经度信息
                val radius = location.radius //获取定位精度，默认值为0.0f
                // 获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
                val coorType = location.coorType
                // 获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
                val errorCode = location.locType
                // 获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明

                Logger.d("$latitude, $longitude, errorCode:$errorCode, version:${locationClient.version}")
                val addr = location.addrStr // 获取详细地址信息
                val country = location.country // 获取国家
                val province = location.province // 获取省份
                val city = location.city // 获取城市
                val district = location.district // 获取区县
                val street = location.street // 获取街道信息
                val adcode = location.adCode // 获取adcode
                val town = location.town // 获取乡镇信息


                requestWeather(context, "$longitude,$latitude")
                locationClient.stop()
            }
        })
        val option = LocationClientOption().apply {
            // 可选，设置定位模式，默认高精度
            // LocationMode.Hight_Accuracy：高精度；
            // LocationMode. Battery_Saving：低功耗；
            // LocationMode. Device_Sensors：仅使用设备；
            // LocationMode.Fuzzy_Locating, 模糊定位模式；v9.2.8版本开始支持，可以降低API的调用频率，但同时也会降低定位精度；
            locationMode = LocationClientOption.LocationMode.Hight_Accuracy

            // 可选，设置返回经纬度坐标类型，默认GCJ02
            // GCJ02：国测局坐标；
            // BD09ll：百度经纬度坐标；
            // BD09：百度墨卡托坐标；
            // 海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标
            setCoorType("bd09ll")

            // 可选，首次定位时可以选择定位的返回是准确性优先还是速度优先，默认为速度优先
            // 可以搭配setOnceLocation(Boolean isOnceLocation)单次定位接口使用，当设置为单次定位时，setFirstLocType接口中设置的类型即为单次定位使用的类型
            // FirstLocType.SPEED_IN_FIRST_LOC:速度优先，首次定位时会降低定位准确性，提升定位速度；
            // FirstLocType.ACCUARACY_IN_FIRST_LOC:准确性优先，首次定位时会降低速度，提升定位准确性；
//            setFirstLocType(FirstLocTypefirstLocType)

            // 可选，设置发起定位请求的间隔，int类型，单位ms
            // 如果设置为0，则代表单次定位，即仅定位一次，默认为0
            // 如果设置非0，需设置1000ms以上才有效
            setScanSpan(1000)

            // 可选，设置是否使用卫星定位，默认false
            // 使用高精度和仅用设备两种定位模式的，参数必须设置为true
            isOpenGps = true

            // 可选，设置是否当卫星定位有效时按照1S/1次频率输出卫星定位结果，默认false
            isLocationNotify = true

            // 可选，定位SDK内部是一个service，并放到了独立进程。
            // 设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
            setIgnoreKillProcess(false)

            // 可选，设置是否收集Crash信息，默认收集，即参数为false
            SetIgnoreCacheException(false)

            // 可选，V7.2版本新增能力
            // 如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位
            setWifiCacheTimeOut(5 * 60 * 1000)

            // 可选，设置是否需要过滤卫星定位仿真结果，默认需要，即参数为false
            setEnableSimulateGps(false)

            // 可选，设置是否需要最新版本的地址信息。默认需要，即参数为true
            setNeedNewVersionRgc(true)
        }
        // locationClient为第二步初始化过的LocationClient对象
        // 需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        // 更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        locationClient.locOption = option
        locationClient.start()
    }

    private fun requestWeather(context: Context, location: String) {
        viewModelScope.launch {
            val prefsStream = weatherPrefsFlow(context)
            val weatherStream = flow {
                emit(weatherRepository.weather(location))
            }
            combine(prefsStream, weatherStream, ::Pair)
                .asResult()
                .map { result ->
                    when (result) {
                        is Result.Success -> {
                            val (prefs, weather) = result.data
                            WeatherUiState.Success(prefs, weather)
                        }
                        is Result.Loading -> {
                            WeatherUiState.Loading
                        }
                        is Result.Error -> {
                            WeatherUiState.Error
                        }
                    }
                }
                .collect {
                    _uiState.value = it
                }
        }
    }

    fun saveMode24H(context: Context, mode24H: WeatherPrefs.Mode) {
        viewModelScope.launch {
            updateMode24H(context, mode24H)
        }
    }

    fun saveMode15D(context: Context, mode15D: WeatherPrefs.Mode) {
        viewModelScope.launch {
            updateMode15D(context, mode15D)
        }
    }
}

sealed interface WeatherUiState {
    data class Success(
        val prefs: WeatherPrefs,
        val weather: Weather,
    ) : WeatherUiState

    object Error : WeatherUiState
    object Loading : WeatherUiState
    object Idle : WeatherUiState
}
