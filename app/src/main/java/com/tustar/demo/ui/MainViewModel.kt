package com.tustar.demo.ui

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tustar.demo.codegen.generateDemos
import com.tustar.demo.data.Weather
import com.tustar.demo.ui.recorder.RecorderInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    val weatherState = MutableStateFlow<Weather?>(null)
    val opsResultState = MutableStateFlow<AppOpsResult?>(null)
    val recorderInfoState = MutableStateFlow(RecorderInfo())

    fun createDemos() = flow {
        emit(generateDemos().groupBy { it.group })
    }
}

data class AppOpsResult(
    var visible: Boolean = false,
    @StringRes var title: Int = -1,
    var nextAction: () -> Unit = {},
)