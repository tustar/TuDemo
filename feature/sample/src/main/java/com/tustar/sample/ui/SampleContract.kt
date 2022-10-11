package com.tustar.sample.ui

import com.tustar.codegen.Sample

class SampleContract {

    data class State(
        val group: Map<String, List<Sample>> = mapOf(),
        val selectedSample: Sample? = null,
        val isDetailOnlyOpen: Boolean = false,
        val loading: Boolean = false,
        val error: String? = null
    )

    sealed class Effect {
        object DataWasLoaded : Effect()
    }
}