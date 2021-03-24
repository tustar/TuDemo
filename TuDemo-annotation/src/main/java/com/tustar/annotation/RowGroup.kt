package com.tustar.annotation

import androidx.annotation.StringRes
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS

const val GROUP_CUSTOM_WIDGET_ID = 1
const val GROUP_SYSTEM_WIDGET_ID = 2
const val GROUP_JETPACK_ID = 3
const val GROUP_OPTIMIZE = 4

@Target(CLASS)
@Retention(RUNTIME)
annotation class RowGroup(
    val id: Int,
    @StringRes val name: Int
)