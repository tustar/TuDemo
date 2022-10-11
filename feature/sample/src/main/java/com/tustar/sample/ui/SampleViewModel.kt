package com.tustar.sample.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tustar.codegen.Sample
import com.tustar.ui.ContentType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SamplesViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(
        SampleContract.State(
            group = mapOf(),
            loading = true
        )
    )
        private set

    var effects = Channel<SampleContract.Effect>(UNLIMITED)
        private set

    init {
        viewModelScope.launch {
            val group = Sample.generateSamples().groupBy { it.group }
            state = state.copy(group = group, loading = false)
            effects.send(SampleContract.Effect.DataWasLoaded)
        }
    }

    fun setSelectedSample(sample: Sample, contentType: ContentType) {
        state = state.copy(
            selectedSample = sample,
            isDetailOnlyOpen = contentType == ContentType.SINGLE_PANE
        )
    }

    fun closeDetailScreen() {
        state = state.copy(
            isDetailOnlyOpen = false,
            selectedSample = state.group.values.flatten().first(),
        )
    }
}


