package com.tustar.demo.ui.home

import androidx.lifecycle.ViewModel
import com.tustar.demo.data.gen.generateDemos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class DemoViewModel @Inject constructor() : ViewModel() {
    fun createDemos() = flow {
        emit(generateDemos().groupBy { it.group })
    }
}