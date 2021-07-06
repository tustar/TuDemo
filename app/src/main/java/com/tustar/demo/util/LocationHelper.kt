package com.tustar.demo.util

import android.content.Context
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener

class LocationHelper constructor(applicationContext: Context) {

    private val locationClient: AMapLocationClient by lazy {
        AMapLocationClient(applicationContext)
    }
    private val locationOption: AMapLocationClientOption by lazy {
        AMapLocationClientOption().apply {
            locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
            // 设置单次定位
            isOnceLocation = true
            isOnceLocationLatest = true
            // 自定义连续定位, SDK默认采用连续定位模式，时间间隔2000ms
//            interval = 1000
            // 单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
//            httpTimeOut = 20000
            // 设置定位同时是否需要返回地址描述
            isNeedAddress = true
            // 关闭缓存机制
//            isLocationCacheEnable = false
            // 设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
//            locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
//            locationClient.stopLocation()
//            locationClient.startLocation()
        }
    }

    fun setLocationListener(listener: AMapLocationListener) {
        // 设置定位回调监听
        locationClient.setLocationListener(listener)
        // 给定位客户端对象设置定位参数
        locationClient.setLocationOption(locationOption)
    }

    fun startLocation() {
        // 启动定位
        locationClient.startLocation()
    }

    fun stopLocation() {
        locationClient.stopLocation()
    }

    fun onDestroy() {
        locationClient.onDestroy()
    }
}