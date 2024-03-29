package com.tustar.weather.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            .aspectRatio(LocalWeatherSize.current.aspectRatio)
    ) {
        val (cityList, warningList, major, date, feelsLike, aqi) = createRefs()
        // cityList
        CityList(
            modifier = Modifier.constrainAs(cityList) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            }, city = city
        )
        // warningList
        WarningList(
            modifier = Modifier
                .constrainAs(warningList) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(cityList.bottom)
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
            }, weatherNow = weatherNow
        )
        // Date
        Text(text = WeatherUtils.gregorianAndLunar(LocalContext.current),
            style = LocalWeatherSize.current.body1,
            modifier = Modifier.constrainAs(date) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(major.bottom)
            })
        //
        Text(
            text = stringResource(
                id = R.string.weather_feelsLike, formatArgs = arrayOf(
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
                .padding(start = LocalWeatherSize.current.margin.dp, bottom = 13.dp),
            style = LocalWeatherSize.current.body1,
        )
        // Aqi
        Aqi(
            modifier = Modifier.constrainAs(aqi) {
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }, airNow = airNow
        )
    }
}

@Composable
private fun Major(modifier: Modifier, weatherNow: WeatherNow) {
    ConstraintLayout(modifier = modifier) {
        val (temp, unit, daily) = createRefs()
        Text(
            text = weatherNow.temp,
            style = LocalWeatherSize.current.title1,
            modifier = Modifier.constrainAs(temp) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
        )
        Text(text = stringResource(id = R.string.weather_temp_unit),
            style = LocalWeatherSize.current.body1,
            modifier = Modifier.constrainAs(unit) {
                top.linkTo(temp.top, 12.dp)
                start.linkTo(temp.end)
            })
        Text(text = weatherNow.text,
            style = LocalWeatherSize.current.body1,
            modifier = Modifier.constrainAs(daily) {
                baseline.linkTo(temp.baseline)
                start.linkTo(temp.end, 2.dp)
            })
    }
}

@Composable
private fun CityList(modifier: Modifier, city: City) {
    Text(
        text = city.name, style = LocalWeatherSize.current.title2, modifier = modifier.padding(5.dp)
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun WarningList(modifier: Modifier, warnings: List<Warning>) {
    if (warnings.isNotEmpty()) {
        Column(
            modifier = modifier.padding(horizontal = 8.dp, vertical = 2.dp),
        ) {
            if (warnings.size >= 2) {
                val infiniteTransition = rememberInfiniteTransition()
                val index by infiniteTransition.animateValue(
                    initialValue = 0,
                    targetValue = warnings.size,
                    typeConverter = TwoWayConverter(
                        { AnimationVector1D(it.toFloat()) },
                        { it.value.toInt() }
                    ),
                    animationSpec = infiniteRepeatable(
                        animation = tween(10_000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )
                AnimatedContent(
                    targetState = index,
                    transitionSpec = {
                        (slideInVertically { height -> height } + fadeIn()
                                with slideOutVertically { height -> -height } + fadeOut())
                            .using(
                                // Disable clipping since the faded slide-in/out should
                                // be displayed out of bounds.
                                SizeTransform(clip = false)
                            )
                    }) { index ->
                    WarningItem(
                        warning = warnings[index]
                    )
                }
            } else {
                WarningItem(warning = warnings[0])
            }
        }

    }
}

@Composable
private fun WarningItem(
    modifier: Modifier = Modifier, warning: Warning
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(
                id = WeatherUtils.alertIconId(
                    context = LocalContext.current, type = warning.type
                )
            ),
            tint = WeatherUtils.alertLevel(warning.level),
            contentDescription = null,
            modifier = Modifier.width(20.dp)
        )
        Text(
            text = stringResource(
                id = R.string.weather_warning, warning.level, warning.typeName
            ), style = LocalWeatherSize.current.body1, modifier = Modifier.padding(5.dp)
        )
    }
}


@Composable
private fun Aqi(modifier: Modifier, airNow: AirNow) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(
                horizontal = LocalWeatherSize.current.margin.dp, vertical = 5.dp
            )
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
            style = LocalWeatherSize.current.body2,
            modifier = Modifier.padding(start = 2.dp)
        )
        Text(
            text = airNow.category,
            style = LocalWeatherSize.current.body2,
            modifier = Modifier.padding(start = 2.dp)
        )
    }
}

@Preview
@Composable
fun PreviewItemWeatherHeader() {
    ItemWeatherHeader(
        city = WeatherContact.State.city,
        weatherNow = WeatherContact.State.weatherNow,
        warnings = WeatherContact.State.warnings,
        airNow = WeatherContact.State.airNow,
    )
}