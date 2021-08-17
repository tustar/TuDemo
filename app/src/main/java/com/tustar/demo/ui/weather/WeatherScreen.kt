package com.tustar.demo.ui.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tustar.demo.R
import com.tustar.demo.data.Weather

@Composable
fun WeatherScreen(weather: Weather) {
    Column {
        Image(
            painter = painterResource(id = R.drawable.bg_0_d),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )
    }
}