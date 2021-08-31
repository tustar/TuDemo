package com.tustar.demo.ui.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.systemuicontroller.SystemUiController
import com.tustar.data.Weather
import com.tustar.demo.R

@Composable
fun WeatherScreen(
    systemUiController: SystemUiController,
    weather: Weather,
) {
    SideEffect {
        systemUiController.setStatusBarColor(color = Color.Transparent)
    }
    Box {
        Image(
            painter = painterResource(id = R.drawable.bg_0_d),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )

        ItemTodayHeader(weather)
        ItemTodayInfo(weather)
    }
}

@Composable
private fun ItemTodayHeader(weather: Weather) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 72.dp, bottom = 48.dp)
    ) {
        val (temp, unit, daily) = createRefs()

        Text(
            text = weather.now.temp.toString(),
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
            color = Color(0xCCFFFFFF),
            modifier = Modifier.constrainAs(unit) {
                top.linkTo(temp.top, 8.dp)
                start.linkTo(temp.end)
            }
        )

        Text(
            text = weather.now.text,
            color = Color(0xCCFFFFFF),
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier.constrainAs(daily) {
                baseline.linkTo(temp.baseline)
                start.linkTo(temp.end)
            }
        )
    }
}

@Composable
fun ItemTodayInfo(weather: Weather) {

}