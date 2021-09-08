package com.tustar.weather.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.tustar.data.source.remote.WeatherDaily
import com.tustar.weather.R

@Composable
fun ItemWeatherSun(today:WeatherDaily) {
    ConstraintLayout(
        modifier = Modifier
            .itemBackground()
            .fillMaxWidth(),
    ) {
        val (title, content) = createRefs()

        ItemWeatherTopBar(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            id = R.string.weather_sunrise_sunset
        )

        Column(modifier = Modifier
            .padding(vertical = 4.dp)
            .constrainAs(content) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {

        }
    }
}
