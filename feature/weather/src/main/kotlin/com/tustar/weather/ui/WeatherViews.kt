package com.tustar.weather.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.tustar.data.source.remote.AirDaily
import com.tustar.weather.R
import com.tustar.weather.WeatherPrefs
import com.tustar.weather.util.TrendSwitchMode

@Composable
fun ItemWeatherSimple(
    @StringRes titleId: Int,
    content: @Composable (Modifier) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .itemBackground()
            .fillMaxWidth()
    ) {
        val (title, content) = createRefs()

        WeatherTopBar(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            id = titleId,
        )

        content(Modifier
            .constrainAs(content) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
    }
}

@Composable
fun ItemWeatherSwitch(
    @StringRes titleId: Int,
    trendMode: TrendSwitchMode,
    list: @Composable (Modifier) -> Unit,
    trend: @Composable (Modifier) -> Unit
) {
    val context = LocalContext.current
    val (mode, onModeChanged) = trendMode
    var isList by remember { mutableStateOf(mode == WeatherPrefs.Mode.LIST) }
    ConstraintLayout(
        modifier = Modifier
            .itemBackground()
            .fillMaxWidth()
    ) {
        val (switch, title, content) = createRefs()

        WeatherSwitch(
            checked = isList,
            onCheckedChange = {
                isList = it
                val mode = if (it) WeatherPrefs.Mode.LIST else WeatherPrefs.Mode.TREND
                onModeChanged(context, mode)
            },
            modifier = Modifier
                .constrainAs(switch) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .padding(start = 4.dp),
        )

        WeatherTopBar(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            id = titleId
        )

        val modifier = Modifier
            .constrainAs(content) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        if (isList) {
            list(modifier)
        } else {
            trend(modifier)
        }
    }
}

@Composable
fun WeatherSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {

    val checkedTextColor: Color = Color(0xFF79C26B)
    val checkedBgColor: Color = Color(0xFFFFFFFF)
    val uncheckedTextColor: Color = Color(0xFFFFFFFF)
    val uncheckedBgColor: Color = Color(0xFFB2B2B2)
    //
    var isList by remember { mutableStateOf(checked) }
    //　Trend
    val trendTextColor = if (isList) uncheckedTextColor else checkedTextColor
    val trendModifier = if (isList) {
        Modifier
    } else {
        Modifier.background(checkedBgColor, RoundedCornerShape(16.dp))
    }
    // List
    val listTextColor = if (isList) checkedTextColor else uncheckedTextColor
    val listModifier = if (isList) {
        Modifier.background(checkedBgColor, RoundedCornerShape(16.dp))
    } else {
        Modifier
    }

    Row(
        modifier = modifier
            .width(64.dp)
            .background(
                color = uncheckedBgColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(interactionSource = MutableInteractionSource(), indication = null) {
                isList = !isList
                onCheckedChange(isList)
            }
            .padding(0.5.dp)
    ) {
        Text(
            text = stringResource(id = R.string.weather_switch_trend),
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            maxLines = 1,
            color = trendTextColor,
            modifier = trendModifier
                .weight(1.0f)
                .padding(vertical = 1.dp, horizontal = 3.dp)

        )
        Text(
            text = stringResource(id = R.string.weather_switch_list),
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            maxLines = 1,
            color = listTextColor,
            modifier = listModifier
                .weight(1.0f)
                .padding(vertical = 1.dp, horizontal = 3.dp)
        )
    }
}

@Composable
fun WeatherTopBar(modifier: Modifier, @StringRes id: Int) {
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

@Composable
fun WeatherAqi(modifier: Modifier = Modifier, airDaily: AirDaily?) {
    Text(
        text = airDaily?.aqi?.toString() ?: "N/A",
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
        color = Color.White,
        modifier = modifier
            .padding(top = 2.dp)
            .width(30.dp)
            .background(WeatherUtils.aqiColor(airDaily?.aqi ?: 0), RoundedCornerShape(2.5.dp)),
    )
}