package com.tustar.sample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tustar.codegen.Sample
import com.tustar.ui.ContentType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SamplesViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(SampleUIState(emptyMap()))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val groupSample = Sample.generateSamples().groupBy { it.group }
            _uiState.value = SampleUIState(groupSample)
        }
    }

    fun setSelectedSample(sample: Sample, contentType: ContentType) {
        _uiState.value = _uiState.value.copy(
            selectedSample = sample,
            isDetailOnlyOpen = contentType == ContentType.SINGLE_PANE
        )
    }

    fun closeDetailScreen() {
        _uiState.value = _uiState
            .value.copy(
                isDetailOnlyOpen = false,
                selectedSample = _uiState.value.group.values.flatten().first(),
            )
    }
}

data class SampleUIState(
    val group: Map<String, List<Sample>>,
    val selectedSample: Sample? = null,
    val isDetailOnlyOpen: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)


