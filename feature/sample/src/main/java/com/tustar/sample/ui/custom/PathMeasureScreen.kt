package com.tustar.sample.ui.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.tustar.annotation.Sample
import com.tustar.sample.R
import com.tustar.sample.widget.PathMeasureView

@Sample(
    group = "sample_group_custom_widget",
    name = "sample_custom_path_measure",
    desc = "sample_custom_path_measure_desc",
    image = "sample_avatar_4",
    createdAt = "2021-01-01 10:15:00",
    updatedAt = "2021-07-02 14:48:00",
)
@Composable
fun PathMeasureScreen() {
    Column {
        PathMeasureView()
    }
}

@Composable
fun PathMeasureView() {
    AndroidView(
        modifier = Modifier.fillMaxSize(), // Occupy the max size in the Compose UI tree
        factory = { context ->
            // Creates custom view
            PathMeasureView(context).apply {
                // Sets up listeners for View -> Compose communication
            }
        },
        update = { view ->
        }
    )
}