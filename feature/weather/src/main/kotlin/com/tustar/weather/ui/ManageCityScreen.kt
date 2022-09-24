package com.tustar.weather.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.google.accompanist.systemuicontroller.SystemUiController
import com.tustar.utils.compose.tint
import com.tustar.weather.Location
import com.tustar.weather.R
import com.tustar.weather.theme.WeatherTheme

const val MAX_CITY_COUNT = 6

@Composable
fun ManageCityScreen(
    systemUiController: SystemUiController,
    viewModel: WeatherViewModel,
    onBackPressed: () -> Boolean,
    onAddCity: () -> Unit,
) {

    val context = LocalContext.current
    val locations by viewModel.cities.collectAsState()
    val values = locations.values.toList()
    var inEditMode by remember { mutableStateOf(false) }
    val showAdd = locations.size < MAX_CITY_COUNT
    val onEditModeChanged: (Boolean) -> Unit = { inEditMode = it }
    val onDelete: (Location) -> Unit = { viewModel.removeCity(context, it) }
    val onItemClick: (Location) -> Unit = {
        if (!inEditMode) {
            viewModel.updateCurrent(it)
            onBackPressed()
        }
    }
    val onItemLongClick: (Location) -> Unit = {
        inEditMode = true
    }

    WeatherTheme {
        systemUiController.setStatusBarColor(WeatherTheme.colors.primary)
        Column {
            Spacer(modifier = Modifier.statusBarsHeight())
            TopBar(inEditMode, onEditModeChanged, showAdd, onAddCity, onBackPressed)
            LazyColumn(
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(values) { location ->
                    ItemCity(location, inEditMode, onItemClick, onItemLongClick, onDelete)
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    inEditMode: Boolean,
    onEditModeChanged: (Boolean) -> Unit,
    showAdd: Boolean,
    onAddCity: () -> Unit,
    onBackPressed: () -> Boolean,
) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.weather_manage_city))
        },
        navigationIcon = {
            val (interactionSource, tint) = tint()
            IconButton(
                onClick = { onBackPressed() },
                interactionSource = interactionSource,
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    contentDescription = null,
                    tint = tint,
                )
            }
        },
        actions = {
            val (editInteraction, editTint) = tint()
            IconButton(
                onClick = { onEditModeChanged(!inEditMode) },
                interactionSource = editInteraction,
            ) {
                Icon(
                    imageVector = if (inEditMode) Icons.Filled.Done else Icons.Filled.Edit,
                    contentDescription = null,
                    tint = editTint,
                )
            }

            val (addInteraction, addTint) = tint()
            if (showAdd) {
                IconButton(
                    onClick = { onAddCity() },
                    enabled = !inEditMode,
                    interactionSource = addInteraction,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                        tint = addTint,
                    )
                }
            }
        },
        modifier = Modifier.fillMaxWidth(),
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ItemCity(
    location: Location,
    inEditMode: Boolean,
    onItemClick: (Location) -> Unit,
    onItemLongClick: (Location) -> Unit,
    onDelete: (Location) -> Unit,
) {
//    235CB3
//            4CA8DB
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(dimensionResource(R.dimen.weather_manage_city_item_height))
            .combinedClickable(
                onClick = {
                    onItemClick(location)
                },
                onLongClick = {
                    onItemLongClick(location)
                },
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF235CB3), Color(0xFF4CA8DB))
                ),
                shape = RoundedCornerShape(5.dp)
            ),
    ) {
        Text(
            text = location.name,
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(
                    horizontal = 16.dp,
                )
                .placeholder(
                    visible = false,
                    color = Color.Gray,
                    // optional, defaults to RectangleShape
                    shape = RoundedCornerShape(4.dp),
                    highlight = PlaceholderHighlight.shimmer(
                        highlightColor = Color.White,
                    ),
                )
        )
        if (inEditMode) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 16.dp, end = 16.dp)
                    .align(Alignment.TopEnd).clickable {
                        onDelete(location)
                    },
                tint = Color.White,
            )
            Icon(
                imageVector = Icons.Filled.SwapVert,
                contentDescription = null,
                modifier = Modifier
                    .padding(bottom = 16.dp, end = 16.dp)
                    .align(Alignment.BottomEnd)
                    .clickable {
                        onDelete(location)
                    },
                tint = Color.White,
            )

        }
    }
}