package com.tustar.annotation

import androidx.annotation.IdRes
import androidx.annotation.StringRes


@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RowDemo(
        @StringRes val name: Int,
        val groupId: Int,
        @IdRes val actionId: Int,
        val parentId: Int = -1,
        val isMenu: Boolean = false)