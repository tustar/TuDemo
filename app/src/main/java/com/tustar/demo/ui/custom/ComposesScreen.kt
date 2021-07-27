package com.tustar.demo.ui.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.tustar.annotation.DemoItem
import com.tustar.demo.R
import com.tustar.demo.ui.DetailTopBar


@DemoItem(
    group = R.string.group_custom_widget,
    item = R.string.custom_composes_example,
    createdAt = "2021-03-12 17:00:00",
    updatedAt = "2021-07-02 10:56:00",
)
@Composable
fun ComposesScreens() {
    Column {
        DetailTopBar()
    }
//    var offsetX by remember { mutableStateOf(0f) }
    val boxSideLengthDp = 50.dp
//    val boxSideLengthPx =
}
