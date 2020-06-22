package com.tustar.annotation

import androidx.annotation.StringRes

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class RowGroup(
        val id: Int,
        @StringRes val name: Int)