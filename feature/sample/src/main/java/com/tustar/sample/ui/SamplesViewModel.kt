package com.tustar.sample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tustar.codegen.Sample
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SamplesViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(SampleState(emptyMap()))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val groupSample = Sample.generateSamples().groupBy { it.group }
            _uiState.value = SampleState(groupSample)
        }
    }

    class SampleState(
        val group: Map<String, List<Sample>>
    )
}


