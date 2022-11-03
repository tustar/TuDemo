package com.tustar.sample.ui.custom

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tustar.annotation.Sample
import com.tustar.ui.design.theme.DemoTheme


@Sample(
    group = "sample_group_custom_widget",
    name = "sample_custom_composes_example",
    desc = "sample_custom_composes_example_desc",
    image = "sample_avatar_express",
    createdAt = "2021-03-12 17:00:00",
    updatedAt = "2021-07-02 10:56:00",
)
@Preview
@Composable
fun TextWithPaddingToBaselinePreview() {
    DemoTheme() {
        Text("Hi there!", Modifier.firstBaselineToTop(32.dp))
    }
}

@Preview
@Composable
fun TextWithNormalPaddingPreview() {
    Text("Hi there!", Modifier.padding(top = 32.dp))
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
