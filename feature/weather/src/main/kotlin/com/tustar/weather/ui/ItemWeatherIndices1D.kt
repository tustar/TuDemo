package com.tustar.weather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.flowlayout.FlowRow
import com.tustar.data.source.remote.IndicesDaily
import com.tustar.ui.design.shape.CupHeadRoundedCornerShape
import com.tustar.weather.R


@Composable
fun ItemWeatherIndices1D(indices1D: List<IndicesDaily>) {
    ItemWeatherTitle(R.string.weather_indices) {
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

        FlowRow() {
            indices1D.forEach {
                ItemLife(
                    modifier = Modifier
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) {
                            openDialog = true
                            indicesDaily = it
                        }
                        .fillMaxWidth(0.33f)
                        .aspectRatio(1.8f),
                    indicesDaily = it
                )
            }

        }
    }
}

@Composable
private fun ItemLife(modifier: Modifier, indicesDaily: IndicesDaily) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(
                    id = WeatherUtils.lifeIconId(
                        context = LocalContext.current,
                        type = indicesDaily.type
                    )
                ),
                contentDescription = null,
                modifier = Modifier
                    .width(64.dp)
            )
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = indicesDaily.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                )
                Text(
                    text = indicesDaily.category,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun LifeDialog(indicesDaily: IndicesDaily, onDismiss: (Boolean) -> Unit) {

    Dialog(
        onDismissRequest = { onDismiss(false) },
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary,
                        )
                    ), CupHeadRoundedCornerShape(8.dp, 54.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Image(
                painter = painterResource(
                    id = WeatherUtils.lifeIconId(
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
                        color = MaterialTheme.colorScheme.inversePrimary,
                        shape = CircleShape
                    )
                    .padding(12.dp)

            )
            Text(
                text = indicesDaily.name,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                modifier = Modifier.padding(top = 12.dp)
            )
            Text(
                text = indicesDaily.category,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = indicesDaily.text,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFFF0F0ED),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3.0f)
                    .padding(horizontal = 30.dp, vertical = 24.dp),
            )
        }
    }
}