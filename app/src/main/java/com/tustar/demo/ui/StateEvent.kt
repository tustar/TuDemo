package com.tustar.demo.ui

data class StateEvent<T>(
    val state: T,
    val onEvent: (T) -> Unit
)