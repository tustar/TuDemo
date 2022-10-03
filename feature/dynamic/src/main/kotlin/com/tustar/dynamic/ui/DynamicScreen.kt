package com.tustar.dynamic.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.tustar.dynamic.R
import com.tustar.ui.design.component.DemoTopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicScreen() {
    Scaffold(
        topBar = {
            DemoTopAppBar(
                titleRes = R.string.dynamic_feature,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = Color.Transparent,
    ) { innerPadding ->
        Text(
            text = stringResource(id = R.string.dynamic_feature),
            modifier = Modifier.padding(innerPadding)
        )
    }
}