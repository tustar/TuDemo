package com.tustar.annotation

import androidx.annotation.StringRes
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*


@Target(CLASS, FUNCTION, LOCAL_VARIABLE)
@Retention(RUNTIME)
annotation class DemoItem(
    @StringRes val group: Int,
    @StringRes val item: Int,
    val createdAt : String,
    val updatedAt : String,
)