package com.tustar.sample.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tustar.codegen.Sample

@Composable
fun SampleDetail(
    sample: Sample,
    isFullScreen: Boolean = true,
    modifier: Modifier = Modifier.fillMaxSize(),
    onBackPressed: () -> Unit = {}
) {
    LazyColumn(modifier = modifier.background(MaterialTheme.colorScheme.inverseOnSurface)) {
        item {
            SampleDetailAppBar(sample, isFullScreen) {
                onBackPressed()
            }
        }
        item {
            SampleDetailItem(sample = sample)
        }
        item {
            SampleDetailItem(sample = sample)
        }
        item {
            SampleDetailItem(sample = sample)
        }
        item {
            SampleDetailItem(sample = sample)
        }
    }
}