package com.tustar.annotation

import androidx.annotation.StringRes
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*


@Target(CLASS, FUNCTION, LOCAL_VARIABLE)
@Retention(RUNTIME)
annotation class DemoItem(
    @StringRes val group: Int,
    @StringRes val item: Int,
    val createdAt : String = "2021-03-28 21:00:00",
    val updatedAt : String = "2021-03-30 21:00:00",
)