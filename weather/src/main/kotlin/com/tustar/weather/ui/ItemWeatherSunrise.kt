package com.tustar.weather.ui

import android.graphics.Path
import android.graphics.PathMeasure
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.tustar.data.source.remote.WeatherDaily
import com.tustar.ktx.compose.res.vectorResource
import com.tustar.weather.R

@Composable
fun ItemWeatherSunrise(today: WeatherDaily) {
    ItemWeatherSimple(R.string.weather_sunrise_sunset) { modifier ->
        ConstraintLayout(modifier = modifier) {
            val (sunPath, sunrise, sunset) = createRefs()
            DrawSunPath(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.weather_sun_path_height))
                    .padding(start = 16.dp, end = 16.dp, bottom = 5.dp)
                    .constrainAs(sunPath) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    },
                WeatherUtils.movedPercent(today.sunrise, today.sunset),
            )
            Text(
                text = stringResource(R.string.weather_sunrise, today.sunrise),
                fontSize = 13.sp,
                color = Color(0xFF191919),
                modifier = Modifier
                    .constrainAs(sunrise) {
                        start.linkTo(parent.start)
                        top.linkTo(sunPath.bottom)
                    },
            )

            Text(
                text = stringResource(R.string.weather_sunset, today.sunset),
                fontSize = 13.sp,
                color = Color(0xFF191919),
                modifier = Modifier
                    .constrainAs(sunset) {
                        end.linkTo(parent.end)
                        top.linkTo(sunPath.bottom)
                    },
            )
        }
    }
}

@Composable
private fun DrawSunPath(
    modifier: Modifier, percent: Float,
) {
    val sun = ImageBitmap.vectorResource(id = R.drawable.ic_sun)
    var rising by remember { mutableStateOf(false) }
    rising = true
    val progress by animateFloatAsState(
        targetValue = if (rising) percent else 0.0f,
        animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
    )
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val color = Color(0xFFF28D00)
        val colors = listOf(
            Color(0xFFFFFF99),
            Color(0x20FFFF99)
        )
        drawCircle(
            color = color,
            center = Offset(0.0f, height),
            radius = 6.0f,
        )
        drawCircle(
            color = color,
            center = Offset(width, height),
            radius = 6.0f,
        )
        val path = Path().apply {
            moveTo(0.0f, height)
            cubicTo(
                size.center.x * 0.5f, height - width * 0.45f,
                size.center.x * 1.5f, height - width * 0.45f,
                width, height
            )
        }
        drawPath(
            path = path.asComposePath(),
            color = color,
            style = Stroke(
                width = 4.0f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(8.0f, 8.0f))
            ),
        )
        //
        val pathMeasure = PathMeasure(path, false)
        val dst = Path().apply {
            reset()
            moveTo(0.0f, height)
        }
        val stopD = pathMeasure.length * progress
        pathMeasure.getSegment(0.0F, stopD, dst, true)
        val pos = FloatArray(2)
        pathMeasure.getPosTan(stopD, pos, null)
        drawPath(
            path = dst.asComposePath(),
            color = color,
            style = Stroke(width = 4.0f),
        )
        dst.lineTo(pos[0], height)
        drawPath(
            path = dst.asComposePath(),
            brush = Brush.verticalGradient(colors)
        )
        drawImage(
            image = sun,
            topLeft = Offset(pos[0] - sun.width / 2.0f, pos[1] - sun.height / 2.0f)
        )
    }
}
