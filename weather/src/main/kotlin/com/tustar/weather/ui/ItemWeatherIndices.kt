package com.tustar.weather.ui

import LazyHorizontalGrid
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.tustar.data.source.remote.IndicesDaily
import com.tustar.weather.R
import com.tustar.weather.shape.CupHeadRoundedCornerShape
import items
import java.time.format.TextStyle


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemWeatherIndices(indices: List<IndicesDaily>) {
    ConstraintLayout(
        modifier = Modifier
            .itemBackground()
            .fillMaxWidth()
    ) {
        val (title, content) = createRefs()

        ItemWeatherTopBar(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            id = R.string.weather_indices
        )

        var openDialog by remember { mutableStateOf(false) }
        var indicesDaily by remember {
            mutableStateOf<IndicesDaily?>(null)
        }
        indicesDaily?.let { indicesDaily ->
            if (openDialog) {
                LifeDialog(
                    indicesDaily = indicesDaily,
                    onDismiss = { openDialog = it })
            }
        }
        //
        LazyHorizontalGrid(
            cells = GridCells.Fixed(2),
            modifier = Modifier
                .constrainAs(content) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            items(items = indices) {
                ItemLife(
                    modifier = Modifier
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) {
                            openDialog = true
                            indicesDaily = it
                        }
                        .fillParentMaxWidth(0.33f)
                        .padding(vertical = 10.dp),
                    indicesDaily = it
                )
            }
        }
    }
}

@Composable
private fun ItemLife(modifier: Modifier, indicesDaily: IndicesDaily) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(
                id = WeatherIcons.lifeIconId(
                    context = LocalContext.current,
                    type = indicesDaily.type
                )
            ),
            contentDescription = null,
            modifier = Modifier
                .width(40.dp)
        )
        Text(
            text = indicesDaily.category,
            fontSize = 16.sp,
            color = Color(0xFF000000),
        )
        Text(
            text = indicesDaily.name,
            fontSize = 13.sp,
            color = Color(0xFF666666),
        )
    }
}

@Composable
fun LifeDialog(indicesDaily: IndicesDaily, onDismiss: (Boolean) -> Unit) {

    Dialog(
        onDismissRequest = { onDismiss(false) },
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .background(Color.White, CupHeadRoundedCornerShape(8.dp, 54.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Image(
                painter = painterResource(
                    id = WeatherIcons.lifeIconId(
                        context = LocalContext.current,
                        type = indicesDaily.type
                    )
                ),
                contentDescription = null,
                modifier = Modifier
                    .padding(6.dp)
                    .size(96.dp, 96.dp)
                    .border(
                        width = 0.5.dp,
                        color = Color(0XFFDBDBDB),
                        shape = CircleShape
                    )
                    .padding(12.dp)

            )
            Text(
                text = indicesDaily.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp, top = 8.dp),
            )
            Text(
                text = indicesDaily.category,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF43abff),
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp, top = 2.dp),
            )
            Text(
                text = indicesDaily.text,
                fontSize = 15.sp,
                textAlign = TextAlign.Start,
                color = Color(0xFF333333),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 32.dp),
            )
        }
    }
}