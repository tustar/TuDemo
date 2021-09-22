package com.tustar.weather.util

data class StateEvent<T>(
    val state: T,
    val onEvent: (T) -> Unit
)