package com.tustar.annotation

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION


@Target(CLASS, FUNCTION)
@Retention(RUNTIME)
annotation class RowDemo(
        val groupId: Int,
        @StringRes val name: Int,
        @IdRes val actionId: Int,
        val parentId: Int = -1)