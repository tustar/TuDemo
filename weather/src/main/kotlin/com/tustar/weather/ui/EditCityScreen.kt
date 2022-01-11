package com.tustar.weather.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tustar.weather.Location
import com.tustar.weather.R

const val MAX_CITY_COUNT = 6

@Composable
fun EditCityScreen(
    viewModel: WeatherViewModel,
    closeDrawer: () -> Unit,
    isEditView: Boolean,
    onEditViewChanged: (Boolean) -> Unit,
) {

    val context = LocalContext.current
    val locations by viewModel.cities.collectAsState()
    val values = locations.values.toList()
    var isEditMode by remember { mutableStateOf(false) }
    val isAddEnable = locations.size < MAX_CITY_COUNT
    val onEditModeChanged: (Boolean) -> Unit = { isEditMode = it }
    val onDelete: (Location) -> Unit = { viewModel.onRemoveCity(context, it) }
    val onItemClick: (Location) -> Unit = {
        viewModel.onUpdateCurrent(it)
        closeDrawer()
    }

    TopBar(isEditMode, onEditModeChanged, isEditView, onEditViewChanged, isAddEnable)
    LazyColumn {
        items(values) { location ->
            EditView(location, isEditMode, onItemClick, onDelete)
        }
    }
}

@Composable
private fun TopBar(
    isEditMode: Boolean,
    onEditModeChanged: (Boolean) -> Unit,
    isEditView: Boolean,
    onEditViewChanged: (Boolean) -> Unit,
    isAddEnable: Boolean,
) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.weather_manage_city))
        },
        navigationIcon = {
            IconButton(onClick = { onEditModeChanged(!isEditMode) }) {
                Icon(
                    imageVector = if (isEditMode) Icons.Filled.Done else Icons.Filled.Edit,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        },
        actions = {
            IconButton(onClick = { onEditViewChanged(!isEditView) },
                enabled = isAddEnable) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun EditView(
    location: Location,
    isEditMode: Boolean,
    onItemClick: (Location) -> Unit,
    onDelete: (Location) -> Unit,
) {
    Row {
        Text(text = location.name,
            color = if (isEditMode) Color.Red else Color.Cyan,
            modifier = Modifier.padding(vertical = 16.dp)
                .clickable {
                    onItemClick(location)
                })
        if (isEditMode) {
            Icon(
                imageVector = Icons.Rounded.DeleteForever,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 16.dp).clickable {
                        onDelete(location)
                    },
                tint = Color.White,
            )
        }
    }
}