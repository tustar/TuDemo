package com.tustar.weather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
            .fillMaxWidth()
            .padding(top = 96.dp),
    ) {
        val (warning, temp, unit, daily, lunar, air) = createRefs()

        ItemWarnings(
            modifier = Modifier
                .background(
                    Color(0x33000000), RoundedCornerShape(16.dp)
                )
                .constrainAs(warning) {
                    bottom.linkTo(temp.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            warnings = warnings
        )

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

                },
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

        ItemDate(modifier = Modifier.constrainAs(lunar) {
            top.linkTo(temp.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })

        ItemAirNow(
            modifier = Modifier
                .padding(top = 96.dp)
                .constrainAs(air) {
                    top.linkTo(temp.bottom)
                    end.linkTo(parent.end)
                }, airNow = airNow
        )
    }
}

@Composable
private fun ItemWarnings(modifier: Modifier, warnings: List<Warning>) {
    if (warnings.isNotEmpty()) {
        ItemWarningRow(
            modifier = modifier
                .padding(horizontal = 8.dp, vertical = 2.dp),
            warning = warnings[0]
        )
    }
}

@Composable
private fun ItemWarningRow(
    modifier: Modifier = Modifier,
    warning: Warning
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(
                id = WeatherIcons.alertIconId(
                    context = LocalContext.current,
                    type = warning.type,
                    level = warning.level
                )
            ),
            contentDescription = null,
            modifier = Modifier.width(20.dp)
        )
        Text(
            text = stringResource(
                id = R.string.weather_warning,
                warning.level,
                warning.typeName
            ),
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
private fun ItemDate(modifier: Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = WeatherHelper.gregorianAndLunar(LocalContext.current),
            color = Color(0xCCFFFFFF),
            fontSize = 14.sp,
        )
    }
}

@Composable
private fun ItemAirNow(modifier: Modifier, airNow: AirNow) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(5.dp)
            .background(
                Color(0x33000000), RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 8.dp, vertical = 2.dp),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_leaf),
            contentDescription = null,
            tint = WeatherHelper.aqiColor(airNow.aqi),
            modifier = Modifier
                .width(24.dp)
                .padding(2.dp)
        )
        Text(
            text = airNow.aqi.toString(),
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier
                .padding(start = 2.dp)
        )
        Text(
            text = airNow.category,
            fontSize = 13.sp,
            color = Color.White,
            modifier = Modifier
                .padding(start = 2.dp)
        )
    }
}