package com.tustar.annotation

import androidx.annotation.StringRes
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FIELD

const val GROUP_HEN_ID = 1

@Target(FIELD, CLASS)
@Retention(RUNTIME)
annotation class RowGroup(
        val id: Int,
        @StringRes val name: Int)