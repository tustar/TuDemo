package com.tustar.weather.ktx

import android.text.TextUtils
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationQualityReport
import java.text.SimpleDateFormat
import java.util.*

fun AMapLocation.toParams(): String = "${longitude},${latitude}"

fun AMapLocation.toFormatString(): String =
    StringBuffer().apply {
        //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
        if (errorCode == 0) {
            append("定位成功\n")
            append("定位类型: $locationType\n")
            append("经    度    : $longitude\n")
            append("纬    度    : $latitude\n")
            append("精    度    : ${accuracy}米\n")
            append("提供者    : $provider\n")
            append("速    度    : ${speed}米/秒" + "\n")
            append("角    度    : $bearing\n")
            // 获取当前提供定位服务的卫星个数
            append("星    数    : $satellites\n")
            append("国    家    : $country\n")
            append("省            : $province\n")
            append("市            : $city\n")
            append("城市编码 : $cityCode\n")
            append("区            : $district\n")
            append("区域码   : $adCode\n")
            append("地    址    : $address\n")
            append("兴趣点    : $poiName\n")
            //定位完成的时间
            append("定位时间: ${formatUTC(time, "yyyy-MM-dd HH:mm:ss")}\n")
        } else {
            //定位失败
            append("定位失败" + "\n")
            append("错误码:$errorCode\n")
            append("错误信息:$errorInfo\n")
            append("错误描述:$locationDetail\n")
        }
        append("***定位质量报告***\n")
        append("* WIFI开关：")
        append(if (locationQualityReport.isWifiAble) "开启" else "关闭")
        append("\n")
        append("* GPS状态：${getGPSStatusString(locationQualityReport.gpsStatus)}\n")
        append("* GPS星数：${locationQualityReport.gpsSatellites}\n")
        append("* 网络类型：${locationQualityReport.networkType}\n")
        append("* 网络耗时：${locationQualityReport.netUseTime}\n")
        append("****************\n")
        //定位之后的回调时间
        append("回调时间: ${formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss")}\n")
    }.toString()

/**
 * 获取GPS状态的字符串
 * @param statusCode GPS状态码
 * @return
 */
private fun getGPSStatusString(statusCode: Int): String = when (statusCode) {
    AMapLocationQualityReport.GPS_STATUS_OK -> "GPS状态正常"
    AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER -> "手机中没有GPS Provider，无法进行GPS定位"
    AMapLocationQualityReport.GPS_STATUS_OFF -> "GPS关闭，建议开启GPS，提高定位质量"
    AMapLocationQualityReport.GPS_STATUS_MODE_SAVING ->
        "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量"
    AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION -> "没有GPS定位权限，建议开启gps定位权限"
    else -> ""
}

fun formatUTC(timeMillis: Long, pattern: String?): String =
    SimpleDateFormat(
        if (TextUtils.isEmpty(pattern)) {
            "yyyy-MM-dd HH:mm:ss"
        } else {
            pattern
        }, Locale.CHINA
    ).format(timeMillis)