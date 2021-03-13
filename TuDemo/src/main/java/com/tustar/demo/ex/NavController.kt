package com.tustar.ktx

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController

fun NavController.navigateUpOrFinish(activity: AppCompatActivity) =
    if (navigateUp()) {
        true
    } else {
        activity.finish()
        true
    }