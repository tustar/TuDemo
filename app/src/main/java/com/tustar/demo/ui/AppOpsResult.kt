package com.tustar.demo.ui

import androidx.annotation.StringRes

data class AppOpsResult(
    val tag: String,
    var visible: Boolean = false,
    @StringRes var title: Int = -1,
    var nextAction: () -> Unit = {},
) {
    companion object {
        const val OPS_TAG_LOCATION = "location"
        const val OPS_TAG_AUDIO = "audio"
    }
}