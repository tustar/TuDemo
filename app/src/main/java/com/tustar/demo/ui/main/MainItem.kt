package com.tustar.demo.ui.main

abstract class MainItem {

    abstract fun getType(): Int

    companion object {
        const val TYPE_SECTION = 0
        const val TYPE_CONTENT = 1
    }
}

