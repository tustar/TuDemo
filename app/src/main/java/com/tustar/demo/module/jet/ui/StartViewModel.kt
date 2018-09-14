package com.tustar.demo.module.jet.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class StartViewModel : ViewModel() {
    private val _data = MutableLiveData<String>()
    val data:LiveData<String>
        get() = _data

    init {
        _data.value = "Hello, Jetpack!"
    }
}
