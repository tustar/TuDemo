package com.tustar.demo.ktx

import android.os.Parcelable
import androidx.navigation.NavHostController

fun NavHostController.putParcelable(key: String, value: Parcelable?) {
    currentBackStackEntry?.arguments?.putParcelable(key, value)
}