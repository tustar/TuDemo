package com.tustar.annotation

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*


@Target(CLASS, FUNCTION, LOCAL_VARIABLE)
@Retention(RUNTIME)
annotation class RowDemo(
        val groupId: Int,
        @StringRes val name: Int,
        @IdRes val actionId: Int,
        val parentId: Int = -1)