package com.tustar.demo.ui.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tustar.annotation.DemoItem
import com.tustar.demo.R
import com.tustar.demo.ui.DetailTopBar
import com.tustar.demo.widget.SvgMapView

@DemoItem(
    group = R.string.group_custom_widget,
    item = R.string.custom_svg_china,
    createdAt = "2021-03-12 17:00:00",
    updatedAt = "2021-07-02 10:56:00",
)
@Composable
fun SvgChinaScreen() {
    Column {
        DetailTopBar()
        SvgMapView()
    }
}

@Composable
fun SvgMapView(viewModel: SvgMapViewModel = viewModel()) {
    val provincesState = viewModel.provinces.observeAsState()
    viewModel.parserData(LocalContext.current)
    AndroidView(

        modifier = Modifier.fillMaxSize(), // Occupy the max size in the Compose UI tree
        factory = { context ->
            // Creates custom view
            SvgMapView(context).apply {
                // Sets up listeners for View -> Compose communication
                provincesState.value?.let {
                    provinces = it
                }
            }
        },
        update = { view ->
        }
    )
}