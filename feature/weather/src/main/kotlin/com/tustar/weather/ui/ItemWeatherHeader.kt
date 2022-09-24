package com.tustar.weather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
    airNow: AirNow,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f),
    ) {

        Column(
            modifier = Modifier.align(Alignment.TopCenter)
                .padding(top = 48.dp, bottom = 128.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ItemWarnings(
                modifier = Modifier.background(
                    Color(0x33000000), RoundedCornerShape(16.dp)
                ),
                warnings = warnings
            )

            ConstraintLayout() {
                val (temp, unit, daily) = createRefs()
                Text(
                    text = weatherNow.temp.toString(),
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White,
                    modifier = Modifier.constrainAs(temp) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
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
            }

            ItemDate()
        }

        ItemAirNow(
            modifier = Modifier.align(Alignment.BottomEnd)
                .clickable {

                },
            airNow = airNow
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
        Icon(
            painter = painterResource(
                id = WeatherUtils.alertIconId(
                    context = LocalContext.current,
                    type = warning.type
                )
            ),
            tint = WeatherUtils.alertLevel(warning.level),
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
private fun ItemDate() {
    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = WeatherUtils.gregorianAndLunar(LocalContext.current),
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
            tint = WeatherUtils.aqiColor(airNow.aqi),
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