package com.tustar.sample.ui

import androidx.annotation.StringRes

data class AppOpsResult(
    @StringRes var title: Int,
    @StringRes val content: Int,
    var nextAction: () -> Unit = {},
    var onDoNotShowRationale: ((Boolean) -> Unit)? = null,
)