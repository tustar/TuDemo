package com.tustar.annotation

import androidx.annotation.StringRes
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS

const val GROUP_HEN_ID = 1

@Target(CLASS)
@Retention(RUNTIME)
annotation class RowGroup(
        val id: Int,
        @StringRes val name: Int)