package com.tustar.weather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.tustar.data.source.remote.AirNow
import com.tustar.data.source.remote.City
import com.tustar.data.source.remote.Warning
import com.tustar.data.source.remote.WeatherNow
import com.tustar.weather.R

@Composable
fun ItemWeatherHeader(
    city: City,
    weatherNow: WeatherNow,
    warnings: List<Warning>,
    airNow: AirNow,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.33f)
    ) {
        val (cityList, warningList, major, date, feelsLike, aqi) = createRefs()
        // cityList
        CityList(
            modifier = Modifier
                .constrainAs(cityList) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
            city = city
        )
        // warningList
        WarningList(
            modifier = Modifier
                .constrainAs(warningList) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(major.top)
                }
                .background(
                    Color(0x33000000), RoundedCornerShape(16.dp)
                ),
            warnings = warnings,
        )
        // Major
        Major(
            modifier = Modifier.constrainAs(major) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            weatherNow = weatherNow
        )
        // Date
        Text(
            text = WeatherUtils.gregorianAndLunar(LocalContext.current),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            modifier = Modifier
                .constrainAs(date) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(major.bottom)
                }
        )
        //
        Text(
            text = stringResource(
                id = R.string.weather_feelsLike,
                formatArgs = arrayOf(
                    weatherNow.feelsLike,
                    weatherNow.humidity,
                    weatherNow.windScale,
                )
            ),
            modifier = Modifier
                .constrainAs(feelsLike) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
                .padding(start = 24.dp, bottom = 13.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
        )
        // Aqi
        Aqi(
            modifier = Modifier.constrainAs(aqi) {
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            },
            airNow = airNow
        )
    }
}

@Composable
private fun Major(modifier: Modifier, weatherNow: WeatherNow) {
    ConstraintLayout(modifier = modifier) {
        val (temp, unit, daily) = createRefs()
        Text(
            text = weatherNow.temp.toString(),
            style = MaterialTheme.typography.displayLarge,
            fontSize = 100.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Thin,
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
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFFF0F0ED),
            modifier = Modifier.constrainAs(unit) {
                top.linkTo(temp.top, 12.dp)
                start.linkTo(temp.end)
            }
        )
        Text(
            text = weatherNow.text,
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFFF0F0ED),
            modifier = Modifier.constrainAs(daily) {
                baseline.linkTo(temp.baseline)
                start.linkTo(temp.end, 2.dp)
            }
        )
    }
}

@Composable
private fun CityList(modifier: Modifier, city: City) {
    Text(
        text = city.name,
        style = MaterialTheme.typography.titleLarge,
        color = Color.White,
        modifier = modifier
            .padding(5.dp)
    )
}

@Composable
private fun WarningList(modifier: Modifier, warnings: List<Warning>) {
    if (warnings.isNotEmpty()) {
        WarningItem(
            modifier = modifier
                .padding(horizontal = 8.dp, vertical = 2.dp),
            warning = warnings[0]
        )
    }
}

@Composable
private fun WarningItem(
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
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            modifier = Modifier.padding(5.dp)
        )
    }
}


@Composable
private fun Aqi(modifier: Modifier, airNow: AirNow) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 5.dp)
            .background(
                Color(0x33000000), RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 8.dp, vertical = 2.dp),
    ) {
        val aqi = try {
            airNow.aqi.toInt()
        } catch (e: NumberFormatException) {
            0
        }
        Icon(
            painter = painterResource(id = R.drawable.weather_ic_leaf),
            contentDescription = null,
            tint = WeatherUtils.aqiColor(aqi),
            modifier = Modifier
                .width(24.dp)
                .padding(2.dp)
        )
        Text(
            text = airNow.aqi,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            modifier = Modifier
                .padding(start = 2.dp)
        )
        Text(
            text = airNow.category,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            modifier = Modifier
                .padding(start = 2.dp)
        )
    }
}