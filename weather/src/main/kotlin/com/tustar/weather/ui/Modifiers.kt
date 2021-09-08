package com.tustar.weather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.itemBackground(marginBottom: Dp = 0.dp, vertical: Dp = 10.dp) = this
    .padding(start = 5.dp, top = 5.dp, end = 5.dp, bottom = marginBottom)
    .background(Color(0xCCFFFFFF), RoundedCornerShape(5.dp))
    .padding(horizontal = 5.dp, vertical = vertical)

fun Modifier.itemSelected() = this
    .border(1.dp, Color(0x1A000000), RoundedCornerShape(5.dp))
    .background(Color(0xCCFFFFFF), RoundedCornerShape(5.dp))
