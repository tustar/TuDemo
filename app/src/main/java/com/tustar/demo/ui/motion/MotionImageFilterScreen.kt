package com.tustar.demo.ui.motion

import android.view.LayoutInflater
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.tustar.annotation.DemoItem
import com.tustar.demo.R
import com.tustar.demo.ui.DetailTopBar


@DemoItem(
    group = R.string.group_system_widget,
    item = R.string.sys_motion_image_filter_view,
    createdAt = "2020-07-15 12:10:00",
    updatedAt = "2021-07-02 16:10:00",
)
@Composable
fun MotionImageFilterScreen(demoItem: Int, upPress: () -> Unit) {
    Column {
        DetailTopBar(demoItem, upPress)
        MotionImageFilterView()
    }
}

@Composable
fun MotionImageFilterView() {
    AndroidView(
        modifier = Modifier.fillMaxSize(), // Occupy the max size in the Compose UI tree
        factory = { context ->
            // Creates custom view
            LayoutInflater.from(context)
                .inflate(R.layout.motion_image_filter_view_fragment, null, false)
        },
        update = { view ->
        }
    )
}