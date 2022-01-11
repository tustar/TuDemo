package com.tustar.weather.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.statusBarsHeight

@Composable
fun Drawer(
    viewModel: WeatherViewModel,
    closeDrawer: () -> Unit,
) {

    var isEditView by remember { mutableStateOf(true) }
    val onEditViewChanged: (Boolean) -> Unit = { isEditView = it }

    Column {
        Spacer(modifier = Modifier.statusBarsHeight())
        // Edit
        if (isEditView) {
            EditCityScreen(viewModel, closeDrawer, isEditView, onEditViewChanged)
        }
        // Edit
        else {
            AddCityScreen(viewModel, onEditViewChanged)
        }
    }
}