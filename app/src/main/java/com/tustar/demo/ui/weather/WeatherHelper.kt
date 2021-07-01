package com.tustar.demo.ui.weather

import com.amap.api.location.AMapLocation

fun AMapLocation.toParams(): String = "${longitude},${latitude}"
