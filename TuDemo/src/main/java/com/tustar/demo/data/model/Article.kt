package com.tustar.demo.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
    val title: String,
    val description: String,
    val url: String,
    var img: String? = null,
    val author: String,
    val createAt: Long
) : Parcelable