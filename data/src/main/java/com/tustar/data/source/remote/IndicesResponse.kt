package com.tustar.data.source.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class IndicesResponse(
    code: String,
    fxLink: String,
    refer: Refer,
    updateTime: String,
    val daily: List<IndicesDaily>,
) : Response(code, fxLink, refer, updateTime)

@Parcelize
data class IndicesDaily(
    val category: String,
    val date: String,
    val level: String,
    val name: String,
    val text: String,
    val type: Int
) : Parcelable