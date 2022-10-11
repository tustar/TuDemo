package com.tustar.weather.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tustar.data.source.remote.AirDaily

@Composable
fun ItemWeatherTitle(
    @StringRes titleId: Int,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .background(Color(0x1A000000), RoundedCornerShape(5.dp))
    ) {
        Text(
            text = stringResource(id = titleId),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier.padding(24.dp)
        )
        content()
    }
}

@Composable
fun WeatherImage(modifier: Modifier = Modifier, icon: String) {
    Image(
        painter = painterResource(
            id = WeatherUtils.weatherIconId(
                context = LocalContext.current,
                icon = icon
            )
        ),
        contentDescription = null,
        modifier = modifier
            .padding(vertical = 5.dp)
            .width(40.dp)

    )
}