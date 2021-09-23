package com.tustar.demo.ktx

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavHostController

fun NavHostController.putParcelable(key: String, value: Parcelable?) {
    currentBackStackEntry?.arguments = Bundle().apply {
        putParcelable(key, value)
    }
}

fun <T : Parcelable?> NavHostController.getParcelable(key: String) =
    previousBackStackEntry?.arguments?.getParcelable<T?>(key)

