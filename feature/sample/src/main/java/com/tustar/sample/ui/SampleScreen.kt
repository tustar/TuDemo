package com.tustar.sample.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tustar.codegen.Sample
import com.tustar.ui.ContentType
import com.tustar.ui.NavigationType
import com.tustar.ui.design.shape.FlagShape
import com.tustar.utils.getStringId
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun SampleScreen(
    contentType: ContentType,
    navigationType: NavigationType,
    modifier: Modifier = Modifier,
    viewModel: SamplesViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val effectFlow = viewModel.effects.receiveAsFlow()

    val closeDetailScreen = {
        viewModel.closeDetailScreen()
    }
    val onItemClicked: (Sample, ContentType) -> Unit = { sample, pane ->
        viewModel.setSelectedSample(sample, pane)
    }

    /**
     * When moving from LIST_AND_DETAIL page to LIST page clear the selection and user should see LIST screen.
     */
    LaunchedEffect(key1 = contentType) {
        if (contentType == ContentType.SINGLE_PANE && !state.isDetailOnlyOpen) {
            closeDetailScreen()
        }
    }
    val scaffoldState = rememberScaffoldState()
    // Listen for side effects from the VM
    LaunchedEffect(effectFlow) {
        effectFlow.onEach { effect ->
            if (effect is SampleContract.Effect.DataWasLoaded)
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Samples are loaded.",
                    duration = SnackbarDuration.Short
                )
        }.collect()
    }

    val lazyListState = rememberLazyListState()

    if (contentType == ContentType.DUAL_PANE) {
        SampleDualPaneContent(
            state = state,
            lazyListState = lazyListState,
            modifier = modifier.fillMaxSize(),
            onItemClicked = onItemClicked
        )
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            SampleSinglePaneContent(
                state = state,
                lazyListState = lazyListState,
                modifier = Modifier.fillMaxSize(),
                closeDetailScreen = closeDetailScreen,
                onItemClicked = onItemClicked
            )
            // When we have bottom navigation we show FAB at the bottom end.
            if (navigationType == NavigationType.BOTTOM_NAVIGATION) {
                LargeFloatingActionButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            if (state.loading) {
                LoadingBar()
            }
        } // End Box
    }
}

@Composable
fun SampleSinglePaneContent(
    state: SampleContract.State,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    closeDetailScreen: () -> Unit,
    onItemClicked: (Sample, ContentType) -> Unit
) {
    if (state.selectedSample != null && state.isDetailOnlyOpen) {
        BackHandler {
            closeDetailScreen()
        }
        SampleDetail(sample = state.selectedSample) {
            closeDetailScreen()
        }
    } else {
        Box {
            SampleList(
                modifier = modifier,
                state = state,
                lazyListState = lazyListState,
                contentType = ContentType.SINGLE_PANE,
                onItemClicked = onItemClicked,
            )
            if (state.loading) {
                LoadingBar()
            }
        }
    }
}

@Composable
fun SampleDualPaneContent(
    modifier: Modifier = Modifier,
    state: SampleContract.State,
    lazyListState: LazyListState,
    onItemClicked: (Sample, ContentType) -> Unit
) {
    Row(modifier = modifier) {
        Box {
            SampleList(
                state = state,
                lazyListState = lazyListState,
                contentType = ContentType.DUAL_PANE,
                onItemClicked = onItemClicked
            )
            if (state.loading) {
                LoadingBar()
            }
        }

        SampleDetail(
            modifier = Modifier.weight(1f),
            isFullScreen = false,
            sample = state.selectedSample ?: state.group.values.flatten().first()
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SampleList(
    modifier: Modifier = Modifier,
    state: SampleContract.State,
    lazyListState: LazyListState,
    contentType: ContentType,
    onItemClicked: (Sample, ContentType) -> Unit
) {
    val grouped = state.group
    Column {
        SearchBar(modifier = Modifier.fillMaxWidth())

        LazyColumn(
            modifier = modifier
                .testTag("sample:home"),
            contentPadding = PaddingValues(top = 8.dp),
            state = lazyListState,
        ) {
            grouped.forEach { (group, samples) ->
                stickyHeader {
                    SampleHeader(group)
                }
                items(samples) { sample ->
                    SampleRow(sample = sample) { sample ->
                        onItemClicked(sample, contentType)
                    }
                }
            }
        }
    }
}

@Composable
fun SampleHeader(group: String) {
    val groupId = LocalContext.current.getStringId(group)
    Text(
        text = stringResource(id = groupId),
        modifier = Modifier
            .padding(vertical = 4.dp)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = FlagShape()
            )
            .padding(top = 4.dp, bottom = 4.dp, start = 4.dp, end = 16.dp),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.primary
    )
}


@Composable
fun LoadingBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}