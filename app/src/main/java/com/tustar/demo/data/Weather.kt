package com.tustar.demo.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    val address: String,
    val temp: Int,
    val daily: String
) : Parcelable