package com.tustar.ktx

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Long.toFormatDate() =
    SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(this))!!