package com.tustar.weather.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
fun ItemWeatherTopBar(modifier: Modifier, @StringRes id: Int) {
    Row(
        modifier = modifier.padding(bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id),
            fontSize = 15.sp,
            color = Color(0xFF000000),
        )
    }
}

@Composable
fun ItemWeatherImage(modifier: Modifier = Modifier, icon: Int) {
    Image(
        painter = painterResource(
            id = WeatherIcons.weatherIconId(
                context = LocalContext.current,
                icon = icon
            )
        ),
        contentDescription = null,
        modifier = modifier
            .width(40.dp)
    )
}

@Composable
fun ItemWeatherAqi(modifier: Modifier = Modifier, airDaily: AirDaily?) {
    airDaily?.let {
        Text(
            text = airDaily.aqi.toString(),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = modifier
                .padding(top = 2.dp)
                .width(30.dp)
                .background(WeatherHelper.aqiColor(airDaily.aqi), RoundedCornerShape(2.5.dp)),
        )
    }
}

@Composable
fun WeatherSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {

}