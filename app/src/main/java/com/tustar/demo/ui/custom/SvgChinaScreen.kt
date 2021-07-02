package com.tustar.demo.ui.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.tustar.annotation.DemoItem
import com.tustar.demo.R
import com.tustar.demo.ui.DetailTopBar
import com.tustar.demo.widget.SvgMapView
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@DemoItem(
    group = R.string.group_custom_widget,
    item = R.string.custom_svg_china,
    createdAt = "2021-03-12 17:00:00",
    updatedAt = "2021-07-02 10:56:00",
)
@Composable
fun SvgChinaScreen(demoItem: Int, upPress: () -> Unit) {
    Column {
        DetailTopBar(demoItem, upPress)
        SvgMapView()
    }
}

@Composable
fun SvgMapView() {
    val vm = SvgMapViewModel()
    val provinces = vm.provinces.observeAsState()
    AndroidView(
        modifier = Modifier.fillMaxSize(), // Occupy the max size in the Compose UI tree
        factory = { context ->
            // Creates custom view
            SvgMapView(context).apply {
                // Sets up listeners for View -> Compose communication
            }
        },
        update = { view ->
            provinces.value?.let {
                view.provinces = it
            }
        }
    )
}