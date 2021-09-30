package com.tustar.weather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tustar.weather.R

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