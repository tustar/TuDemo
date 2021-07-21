package com.tustar.demo.ui.recorder

data class RecorderInfo(
    val state: State = State.IDLE,
    val maxAmplitude: Int = 30
)