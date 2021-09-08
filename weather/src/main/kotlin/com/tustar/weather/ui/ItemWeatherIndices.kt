package com.tustar.weather.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.tustar.data.source.remote.IndicesDaily
import com.tustar.weather.R


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

        LazyVerticalGrid(
            cells = GridCells.Fixed(3),
            modifier = Modifier
                .padding(vertical = 4.dp)
                .constrainAs(content) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            items(indices.size) { index ->
                Column(
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = indices[index].category,
                        fontSize = 15.sp,
                        color = Color(0xFF000000),
                    )
                    Text(
                        text = indices[index].name,
                        fontSize = 14.sp,
                        color = Color(0xFF666666),
                    )
                    Button(onClick = { /*TODO*/ }) {

                    }
                }
            }
        }
    }
}