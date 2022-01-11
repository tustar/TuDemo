package com.tustar.weather.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tustar.data.source.remote.City
import com.tustar.weather.R

@Composable
fun AddCityScreen(
    viewModel: WeatherViewModel,
    onEditViewChanged: (Boolean) -> Unit,
) {
    val topCities by viewModel.topCities.collectAsState()
    val searchCities by viewModel.searchCities.collectAsState()
    viewModel.requestTopCities()
    val context = LocalContext.current
    val onItemClick = { city: City ->
        viewModel.onAddCity(context, city)
    }
    val onSearchCities = { key: String ->
        viewModel.onSearchCities(key)
    }
    AddCityContent(searchCities, onSearchCities, topCities, onEditViewChanged, onItemClick)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AddCityContent(
    searchCities: List<City>,
    onSearchCities: (String) -> Unit,
    topCities: List<City>,
    onEditViewChanged: (Boolean) -> Unit,
    onItemClick: (City) -> Unit,
) {
    val onCityClick = { city: City ->
        onEditViewChanged(true)
        onItemClick(city)
    }

    TopAppBar(
        title = {
            Text(text = stringResource(R.string.weather_add_city))
        },
        navigationIcon = {
            IconButton(onClick = { onEditViewChanged(true) }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    contentDescription = null,
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
    //
    SearchView(searchCities, onSearchCities, onCityClick)
    //
    if (searchCities.isNullOrEmpty()) {
        TopCityView(topCities, onCityClick)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchView(
    searchCities: List<City>?,
    onSearchCities: (String) -> Unit,
    onCityClick: (City) -> Unit,
) {
    Column {
        var showClearButton by remember { mutableStateOf(false) }
        var value = ""
        val onClearClick = {
            value = ""
        }

        val focusRequester = remember { FocusRequester() }
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    showClearButton = (focusState.isFocused)
                },
            value = value,
            onValueChange = {
                onSearchCities(it)
                showClearButton = it.isNotEmpty()
            },
            label = {},
            textStyle = TextStyle(color = Color.White, fontWeight = FontWeight.Bold),
            singleLine = true,
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            },
            trailingIcon = {
                if (showClearButton) {
                    IconButton(onClick = { onClearClick() }) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "Clear")
                    }
                }

            },
            keyboardActions = KeyboardActions(onDone = {
                //
            }),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            shape = RoundedCornerShape(10f)
        )
        DisposableEffect(Unit) {
            focusRequester.requestFocus()
            onDispose { }
        }

        if (!searchCities.isNullOrEmpty()) {
            var expanded by remember { mutableStateOf(!searchCities.isNullOrEmpty()) }
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)) {
                LazyColumn {
                    items(searchCities) { city ->
                        Row(modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 12.dp)
                            .clickable {
                                onCityClick(city)
                            }) {
                            Text(text = "${city.adm2} - ",
                                color = Color(0xFFFFFFFF))
                            Text(text = "${city.name}",
                                color = Color(0x66FFFFFF))
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
private fun TopCityView(
    topCities: List<City>,
    onCityClick: (City) -> Unit,
) {
    Text(text = stringResource(R.string.weather_top_cities),
        modifier = Modifier.padding(vertical = 12.dp))
    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 12.dp)
    ) {
        items(items = topCities) { city ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = city.name,
                    modifier = Modifier.padding(vertical = 12.dp)
                        .clickable {
                            onCityClick(city)
                        })
            }
        }
    }
}

