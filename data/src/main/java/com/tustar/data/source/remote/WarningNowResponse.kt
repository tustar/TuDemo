package com.tustar.data.source.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class WarningNowResponse(
    code: String,
    fxLink: String,
    refer: Refer,
    updateTime: String,
    val warning: List<Warning>
) : HeResponse(code, fxLink, refer, updateTime)

@Parcelize
data class Warning(
    val endTime: String,
    val id: String,
    val level: String,
    val pubTime: String,
    val related: String,
    val sender: String,
    val startTime: String,
    val status: String,
    val text: String,
    val title: String,
    val type: String,
    val typeName: String
) : Parcelable