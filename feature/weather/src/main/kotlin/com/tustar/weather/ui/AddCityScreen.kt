package com.tustar.weather.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.statusBarsHeight
import com.tustar.data.source.remote.City
import com.tustar.utils.compose.selector
import com.tustar.utils.compose.tint
import com.tustar.weather.Location
import com.tustar.weather.R
import com.tustar.weather.theme.WeatherTheme
import com.tustar.weather.util.isSame

@Composable
fun AddCityScreen(
    viewModel: WeatherViewModel,
    onBackPressed: () -> Boolean,
    onBackHome: () -> Boolean,
) {
    val context = LocalContext.current
    val cities by viewModel.cities.collectAsState()
    val topCities by viewModel.topCities.collectAsState()
    val searchCities by viewModel.searchCities.collectAsState()
    val onSearchCities = { key: String ->
        viewModel.searchCities(key)
    }
    //
    viewModel.clearSearch()
    viewModel.requestTopCities()
    //
    WeatherTheme {
        AddCityContent(
            cities.values.toList(), searchCities, onSearchCities, topCities,
            onBackPressed, onBackHome,
        ) { city: City ->
            viewModel.addCity(context, city)
            onBackHome()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AddCityContent(
    cities: List<Location>,
    searchCities: List<City>,
    onSearchCities: (String) -> Unit,
    topCities: List<City>,
    onBackPressed: () -> Boolean,
    onBackHome: () -> Boolean,
    onCityClick: (City) -> Unit,
) {
    Column {
        Spacer(modifier = Modifier.statusBarsHeight())
        TopAppBar(
            title = {
                Text(text = stringResource(R.string.weather_add_city))
            },
            navigationIcon = {
                val (interactionSource, tint) = tint()
                IconButton(
                    onClick = { onBackPressed() },
                    interactionSource = interactionSource
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBackIosNew,
                        contentDescription = null,
                        tint = tint,
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        //
        SearchView(onSearchCities, onBackHome)
        //
        if (searchCities.isNullOrEmpty()) {
            TopCityView(cities, topCities, onCityClick)
        } else {
            SearchDropdown(searchCities, onCityClick)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchView(
    onSearchCities: (String) -> Unit,
    onBackHome: () -> Boolean,
) {

    var showClear by remember { mutableStateOf(false) }
    var value by remember { mutableStateOf("") }
    val onClearClick = {
        value = ""
        showClear = false
        onSearchCities(value)
    }


    val focusRequester = remember { FocusRequester() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextField(
            modifier = Modifier
                .weight(weight = 1.0f)
                .padding(vertical = 16.dp)
                .focusRequester(focusRequester),
            value = value,
            onValueChange = {
                value = it
                onSearchCities(it)
                showClear = it.isNotEmpty()
            },
            placeholder = {
                if (!showClear) {
                    Text(
                        text = stringResource(R.string.weather_search_hint),
                        color = Color(0xFF999999),
                        fontSize = 18.sp,
                    )
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFEDEDED),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            textStyle = TextStyle(color = Color(0xFF596DFF), fontSize = 18.sp),
            singleLine = true,
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            },
            trailingIcon = {
                if (showClear) {
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
            shape = RoundedCornerShape(30f)
        )
        Text(text = stringResource(android.R.string.cancel),
            color = Color(0xFF333333),
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 16.dp)
                .clickable(interactionSource = MutableInteractionSource(), indication = null) {
                    onBackHome()
                })
    }
    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { }
    }
}

@ExperimentalFoundationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SearchDropdown(
    searchCities: List<City>?,
    onCityClick: (City) -> Unit,
) {
    if (!searchCities.isNullOrEmpty()) {
        var expanded by remember { mutableStateOf(!searchCities.isNullOrEmpty()) }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            LazyColumn {
                items(searchCities) { city ->
                    val (interactionSource, color) = selector(
                        normal = Color(0xFF191919),
                        pressed = Color(0xFF596DFF)
                    )
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 12.dp)
                        .combinedClickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onCityClick(city)
                        }) {
                        Text(
                            text = "${city.adm2} - ",
                            color = color,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "${city.name}",
                            color = color,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
private fun TopCityView(
    cities: List<Location>,
    topCities: List<City>,
    onCityClick: (City) -> Unit,
) {
    val location = cities.find { it.auto }
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Row(modifier = Modifier.padding(bottom = 16.dp)) {
            Text(
                text = stringResource(R.string.weather_location),
                color = Color(0xFF191919),
                fontSize = 18.sp,
            )
            Text(
                text = location!!.name,
                color = Color(0xFF596DFF),
                fontSize = 18.sp,
            )
        }

        Divider(color = Color(0xFFEDEDED))
        Text(
            text = stringResource(R.string.weather_top_cities),
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.padding(vertical = 12.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
        ) {
            items(items = topCities) { city ->
                val selected = cities.any { location -> location.isSame(city) }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = city.name,
                        color = if (selected) {
                            Color(0xFF596DFF)
                        } else {
                            Color(0xFF191919)
                        },
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 12.dp)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) {
                                onCityClick(city)
                            })
                }
            }
        }
    }
}

