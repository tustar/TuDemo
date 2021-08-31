package com.tustar.data

import android.os.Parcelable
import com.tustar.data.source.remote.Now
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    val address: String,
    val now: Now,
) : Parcelable