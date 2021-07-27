package com.tustar.demo.ui.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.tustar.annotation.DemoItem
import com.tustar.demo.R
import com.tustar.demo.ui.DetailTopBar
import com.tustar.demo.widget.PathMeasureView

@DemoItem(
    group = R.string.group_custom_widget,
    item = R.string.custom_path_measure,
    createdAt = "2021-01-01 10:15:00",
    updatedAt = "2021-07-02 14:48:00",
)
@Composable
fun PathMeasureScreen() {
    Column {
        DetailTopBar()
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