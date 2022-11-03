package com.tustar.annotation

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*


@Target(CLASS, FUNCTION, LOCAL_VARIABLE)
@Retention(RUNTIME)
annotation class Sample(
    val group: String,
    val name: String,
    val desc: String,
    val image: String = "sample_avatar_express",
    val createdAt: String = "2021-03-28 21:00:00",
    val updatedAt: String = "2021-03-30 21:00:00",
)