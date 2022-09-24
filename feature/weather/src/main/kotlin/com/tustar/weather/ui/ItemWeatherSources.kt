package com.tustar.weather.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tustar.weather.R

@Composable
fun ItemWeatherSources() {
    Text(
        text = stringResource(id = R.string.weather_sources),
        textAlign = TextAlign.Center,
        fontSize = 13.sp,
        color = Color(0xFF666666),
        modifier = Modifier
            .itemBackground(marginBottom = 5.dp)
            .fillMaxWidth(),
    )
}
