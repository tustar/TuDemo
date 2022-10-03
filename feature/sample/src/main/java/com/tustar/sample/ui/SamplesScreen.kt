package com.tustar.sample.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tustar.codegen.Sample
import com.tustar.sample.R
import com.tustar.ui.design.component.DemoToggleButton
import com.tustar.ui.design.component.DemoTopAppBar
import com.tustar.ui.design.icon.DemoIcons
import com.tustar.utils.getStringId


@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SamplesScreen(
    navigateToSample: (Sample) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SamplesViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            DemoTopAppBar(
                titleRes = R.string.sample_feature,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = Color.Transparent,
    ) { innerPadding ->
        SampleList(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            uiState.group.values.flatten(),
            onSampleClick = navigateToSample
        )
    }
}

@Composable
fun SampleList(
    modifier: Modifier,
    samples: List<Sample>,
    onSampleClick: (Sample) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .testTag("sample:home"),
        contentPadding = PaddingValues(top = 8.dp)
    ) {
        items(samples) { sample ->
            SampleRow(sample = sample, onClick = onSampleClick)
        }
    }
}

@Composable
fun SampleRow(
    modifier: Modifier = Modifier,
    itemSeparation: Dp = 16.dp,
    sample: Sample,
    onClick: (Sample) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
                .clickable {

                }
                .padding(vertical = itemSeparation)
        ) {
            val nameId = LocalContext.current.getStringId(sample.name)
            val descId = LocalContext.current.getStringId(sample.desc)
            Column(modifier) {
                Text(
                    text = stringResource(id = nameId),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = stringResource(id = descId),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        DemoToggleButton(
            checked = true,
            onCheckedChange = { },
            icon = {
                Icon(
                    imageVector = DemoIcons.Add,
                    contentDescription = null
                )
            },
            checkedIcon = {
                Icon(
                    imageVector = DemoIcons.Check,
                    contentDescription = null
                )
            }
        )
    }
}
