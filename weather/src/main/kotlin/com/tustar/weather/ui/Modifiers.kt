package com.tustar.weather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.itemBackground() = this
    .padding(horizontal = 4.dp, vertical = 2.dp)
    .background(Color(0xCCFFFFFF), RoundedCornerShape(8.dp))
    .padding(vertical = 4.dp)