package com.tustar.demo.ui.home

import androidx.lifecycle.ViewModel
import com.tustar.demo.data.gen.generateDemos
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DemoViewModel @Inject constructor() : ViewModel() {
    fun createDemos() = generateDemos().groupBy { it.group }
}