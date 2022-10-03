/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tustar.ui.design.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.tustar.ui.R

/**
 * Now in Android icons. Material icons are [ImageVector]s, custom icons are drawable resource IDs.
 */
object DemoIcons {
    val AccountCircle = Icons.Outlined.AccountCircle
    val Add = Icons.Rounded.Add
    val ArrowBack = Icons.Rounded.ArrowBack
    val ArrowDropDown = Icons.Rounded.ArrowDropDown
    val ArrowDropUp = Icons.Rounded.ArrowDropUp
    val Check = Icons.Rounded.Check
    val Close = Icons.Rounded.Close
    val ExpandLess = Icons.Rounded.ExpandLess
    val Fullscreen = Icons.Rounded.Fullscreen
    val Grid3x3 = Icons.Rounded.Grid3x3
    val MoreVert = Icons.Default.MoreVert
    val Person = Icons.Rounded.Person
    val PlayArrow = Icons.Rounded.PlayArrow
    val Search = Icons.Rounded.Search
    val ShortText = Icons.Rounded.ShortText
    val Tag = Icons.Rounded.Tag
    val ViewDay = Icons.Rounded.ViewDay
    val VolumeOff = Icons.Rounded.VolumeOff
    val VolumeUp = Icons.Rounded.VolumeUp
    val Home = R.drawable.ic_home
    val HomeBorder = R.drawable.ic_home_border
    val Weather = R.drawable.ic_weather
    val WeatherBorder = R.drawable.ic_weather_border
    val Article = R.drawable.ic_article
    val ArticleBorder = R.drawable.ic_article_border
    val Dynamic = R.drawable.ic_dynamic
    val DynamicBorder = R.drawable.ic_dynamic_border
}

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}
