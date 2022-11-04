package com.tustar.weather.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun ItemWeatherTitle(
    @StringRes titleId: Int,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(LocalWeatherSize.current.titleMargin.dp)
            .background(Color(0x1A000000), RoundedCornerShape(5.dp))
    ) {
        Text(
            text = stringResource(id = titleId),
            style = LocalWeatherSize.current.title3,
            modifier = Modifier.padding(start = LocalWeatherSize.current.titleMargin.dp, top = LocalWeatherSize.current.titleMargin.dp, bottom = 12.dp)
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
            .width(LocalWeatherSize.current.imgWidth.dp)

    )
}