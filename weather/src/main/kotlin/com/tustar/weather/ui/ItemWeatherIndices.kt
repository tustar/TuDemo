package com.tustar.weather.ui

import LazyHorizontalGrid
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.tustar.data.source.remote.IndicesDaily
import com.tustar.weather.R
import items


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
        indicesDaily?.let { index ->
            if (openDialog) {
                ShowLifeIndex(index = index, onDismiss = { openDialog = it })
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
fun ShowLifeIndex(index: IndicesDaily, onDismiss: (Boolean) -> Unit) {
    Dialog(onDismissRequest = { onDismiss(false) }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Box(
            modifier = Modifier
                .size(270.dp, 300.dp)
                .background(Color.White),
        )
    }
}

//<?xml version="1.0" encoding="utf-8"?>
//<LinearLayout android:gravity="center"
//android:orientation="vertical"
//android:id="@id/linear_container"
//android:background="@color/transparent"
//android:layout_width="fill_parent"
//android:layout_height="fill_parent"
//xmlns:android="http://schemas.android.com/apk/res/android">
//
//<RelativeLayout
//android:layout_width="270.0dip"
//android:layout_height="300.0dip"
//android:layout_centerInParent="true">
//
//<LinearLayout
//android:orientation="vertical"
//android:background="@drawable/shape_rounded_rectangl"
//android:layout_width="fill_parent"
//android:layout_height="350.0dip"
//android:layout_marginTop="@dimen/dp_34">
//
//<LinearLayout
//android:layout_gravity="center_horizontal"
//android:orientation="vertical"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_marginTop="70.0dip">
//
//<TextView
//android:textSize="@dimen/font_size_16"
//android:textStyle="bold"
//android:textColor="@color/color_333333"
//android:layout_gravity="center_horizontal"
//android:id="@id/txt_dialog_life_title"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:text="title" />
//
//<TextView
//android:textSize="@dimen/font_size_18"
//android:textStyle="bold"
//android:textColor="@color/color_43abff"
//android:layout_gravity="center_horizontal"
//android:id="@id/txt_dialog_life_title_less"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_marginTop="2.0dip" />
//</LinearLayout>
//
//<ScrollView
//android:layout_width="fill_parent"
//android:layout_height="fill_parent"
//android:layout_marginLeft="@dimen/dp_26"
//android:layout_marginTop="@dimen/dp_13"
//android:layout_marginRight="@dimen/dp_26"
//android:layout_marginBottom="@dimen/dp_34">
//
//<TextView
//android:textSize="@dimen/font_size_15"
//android:textColor="@color/color_333333"
//android:id="@id/txt_dialog_life_info"
//android:layout_width="fill_parent"
//android:layout_height="fill_parent" />
//</ScrollView>
//</LinearLayout>
//
//<RelativeLayout
//android:background="@drawable/shape_white_circle"
//android:layout_width="96dp"
//android:layout_height="96dp"
//android:layout_centerHorizontal="true">
//
//<ImageView
//android:id="@id/img_dialog_life_icon"
//android:background="@drawable/shape_hollow_circle_dbdbdb"
//android:padding="6dp"
//android:layout_width="fill_parent"
//android:layout_height="fill_parent"
//android:layout_margin="6dp"
//android:scaleType="center" />
//</RelativeLayout>
//</RelativeLayout>
//
//<ImageView
//android:id="@id/img_close"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_marginTop="38.0dip"
//android:src="@drawable/btn_close_white"
//android:scaleType="center" />
//</LinearLayout>