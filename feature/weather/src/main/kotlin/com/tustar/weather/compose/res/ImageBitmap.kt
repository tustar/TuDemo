package com.tustar.weather.compose.res

import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap

@Composable
fun ImageBitmap.Companion.vectorResource(@DrawableRes id: Int): ImageBitmap {
    val context = LocalContext.current
    return AppCompatResources.getDrawable(context, id)!!.toBitmap().asImageBitmap()
}