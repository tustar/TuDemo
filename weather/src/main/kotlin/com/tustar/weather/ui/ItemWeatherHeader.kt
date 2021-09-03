package com.tustar.weather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.tustar.data.source.remote.AirNow
import com.tustar.data.source.remote.Warning
import com.tustar.data.source.remote.WeatherNow
import com.tustar.weather.R

@Composable
fun ItemWeatherHeader(
    weatherNow: WeatherNow,
    warnings: List<Warning>,
    airNow: AirNow
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(top = 48.dp)
            .fillMaxWidth()
    ) {
        val (temp, unit, daily, warning) = createRefs()

        Text(
            text = weatherNow.temp.toString(),
            fontSize = 80.sp,
            fontWeight = FontWeight.Light,
            color = Color.White,
            modifier = Modifier
                .constrainAs(temp) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        Text(
            text = stringResource(id = R.string.weather_temp_unit),
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFFF0F0ED),
            modifier = Modifier.constrainAs(unit) {
                top.linkTo(temp.top, 8.dp)
                start.linkTo(temp.end)
            }
        )

        Text(
            text = weatherNow.text,
            color = Color(0xCCFFFFFF),
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier.constrainAs(daily) {
                baseline.linkTo(temp.baseline)
                start.linkTo(temp.end)
            }
        )

        if (warnings.isNotEmpty()) {
            Text(
                text = stringResource(
                    id = R.string.weather_warning,
                    warnings[0].type,
                    warnings[0].typeName
                ),
                color = Color(0xCCFFFFFF),
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .background(
                        Color(0x33000000), RoundedCornerShape(8.dp)
                    )
                    .constrainAs(warning) {
                        top.linkTo(temp.bottom)
                    }
            )
        }

        ItemAirNow(
            modifier = Modifier
                .padding(top = 96.dp)
                .constrainAs(warning) {
                    top.linkTo(temp.bottom)
                }, airNow = airNow
        )
    }
}

@Composable
fun ItemAirNow(modifier: Modifier, airNow: AirNow) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .background(
                Color(0x33000000), RoundedCornerShape(16.dp)
            )
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_leaf),
            contentDescription = null,
            tint = WeatherHelper.aqiColor(airNow.aqi),
            modifier = Modifier
                .width(24.dp)
                .padding(start = 4.dp)
        )
        Text(
            text = airNow.aqi.toString(),
            fontSize = 15.sp,
            color = Color.White,
            modifier = Modifier
                .padding(start = 4.dp)
        )
        Text(
            text = airNow.category,
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier
                .padding(horizontal = 4.dp)
        )
    }
}