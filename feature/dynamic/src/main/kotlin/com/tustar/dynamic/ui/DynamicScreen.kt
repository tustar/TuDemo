package com.tustar.dynamic.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tustar.dynamic.R


@Composable
fun DynamicScreen() {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        item {
            DynamicItem(
                modifier = Modifier.clickable {

                },
                drawableResource = R.drawable.dynamic_paris_1,
                name = R.string.dynamic_hook
            )
        }
        item {
            DynamicItem(
                modifier = Modifier.clickable {

                },
                drawableResource = R.drawable.dynamic_paris_2,
                name = R.string.dynamic_hot_fix
            )
        }
        item {
            DynamicItem(
                modifier = Modifier.clickable {

                },
                drawableResource = R.drawable.dynamic_paris_3,
                name = R.string.dynamic_re_plugin
            )
        }
        item {
            DynamicItem(
                modifier = Modifier.clickable {

                },
                drawableResource = R.drawable.dynamic_paris_4,
                name = R.string.dynamic_component
            )
        }
        item {
            Spacer(modifier = Modifier.navigationBarsPadding())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DynamicItem(
    modifier: Modifier,
    drawableResource: Int,
    name: Int
) {
    Card(
        modifier = modifier
            .height(200.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(4.dp),
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = drawableResource),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Text(
                text = stringResource(id = name),
                fontSize = 32.sp,
                color = Color.White,
            )
        }
    }
}