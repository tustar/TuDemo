package com.tustar.sample.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tustar.codegen.Sample

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun SamplesScreen(
    viewModel: SamplesViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SampleList(uiState.group.values.flatten())
}

@Composable
fun SampleList(samples: List<Sample>) {
    LazyColumn {
        items(samples) { sample ->
            SampleRow(sample)
        }
    }
}

@Composable
fun SampleRow(sample: Sample) {
    Row {
        Text(text = sample.group)
        Text(text = sample.item)
        Text(text = sample.createdAt)
        Text(text = sample.group)
    }
}
