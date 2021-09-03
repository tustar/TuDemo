package com.tustar.weather.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.tustar.data.source.remote.Hourly
import com.tustar.weather.R

@Composable
fun ItemWeather24h(hourly24: List<Hourly>) {
    ConstraintLayout(
        modifier = Modifier
            .itemBackground()
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        val (title, content) = createRefs()

        Text(
            text = stringResource(R.string.weather_24h_forecast),
            fontSize = 15.sp,
            color = Color(0xFF000000),
            modifier = Modifier
                .padding(vertical = 4.dp)
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        LazyRow(modifier = Modifier
            .padding(vertical = 4.dp)
            .constrainAs(content) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            items(items = hourly24, itemContent = { item ->
                val (isNow, formatFxTime) = WeatherHelper.hourlyTime(item.fxTime)
                val modifier = if (isNow) {
                    Modifier
                        .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
                } else {
                    Modifier
                }
                Column(
                    modifier = modifier
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = formatFxTime,
                        fontSize = 15.sp,
                        color = Color(0xFF000000),
                    )
                    Icon(
                        painter = painterResource(
                            id = WeatherHelper.iconId(
                                LocalContext.current,
                                item.icon
                            )
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                    )
                    Text(
                        text = item.text,
                        fontSize = 14.sp,
                        color = Color(0xFF666666),
                    )
                    Text(
                        text = stringResource(R.string.weather_temp_value, item.temp),
                        fontSize = 15.sp,
                        color = Color(0xFF000000),
                    )
                }
            })
        }
    }
}