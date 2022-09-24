package com.tustar.sample.ui.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tustar.annotation.Sample
import com.tustar.sample.ui.theme.SamplesTheme


@Sample(
    group = "group_custom_widget",
    item = "custom_composes_example",
    createdAt = "2021-07-25 10:15:00",
    updatedAt = "2021-08-02 15:24:00",
)
@Composable
fun ComposesScreens() {
    Column {

    }
}

@Preview
@Composable
fun TextWithPaddingToBaselinePreview() {
    SamplesTheme {
        Text("Hi there!", Modifier.firstBaselineToTop(32.dp))
    }
}

@Preview
@Composable
fun TextWithNormalPaddingPreview() {
    SamplesTheme {
        Text("Hi there!", Modifier.padding(top = 32.dp))
    }
}

fun Modifier.firstBaselineToTop(firstBaselineToTop: Dp) = layout { measurable, constraints ->
    // Measure the composable
    val placeable = measurable.measure(constraints)

    // Check the composable has a first baseline
    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
    val firstBaseline = placeable[FirstBaseline]

    // Height of the composable with padding - first baseline
    val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
    val height = placeable.height + placeableY
    layout(placeable.width, height) {
        // Where the composable gets placed
        placeable.placeRelative(0, placeableY)
    }
}
