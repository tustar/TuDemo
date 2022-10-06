package com.tustar.sample.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tustar.codegen.Sample
import com.tustar.ui.ContentType
import com.tustar.ui.NavigationType
import com.tustar.ui.design.shape.FlagShape
import com.tustar.utils.getStringId

@OptIn(
    ExperimentalLifecycleComposeApi::class,
)
@Composable
fun SampleScreen(
    contentType: ContentType,
    navigationType: NavigationType,
    modifier: Modifier = Modifier,
    viewModel: SamplesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val closeDetailScreen = {
        viewModel.closeDetailScreen()
    }
    val navigateToDetail: (Sample, ContentType) -> Unit = { sample, pane ->
        viewModel.setSelectedSample(sample, pane)
    }

    /**
     * When moving from LIST_AND_DETAIL page to LIST page clear the selection and user should see LIST screen.
     */
    LaunchedEffect(key1 = contentType) {
        if (contentType == ContentType.SINGLE_PANE && !uiState.isDetailOnlyOpen) {
            closeDetailScreen()
        }
    }

    val lazyListState = rememberLazyListState()

    if (contentType == ContentType.DUAL_PANE) {
        SampleDualPaneContent(
            uiState = uiState,
            lazyListState = lazyListState,
            modifier = modifier.fillMaxSize(),
            navigateToDetail = navigateToDetail
        )
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            SampleSinglePaneContent(
                uiState = uiState,
                lazyListState = lazyListState,
                modifier = Modifier.fillMaxSize(),
                closeDetailScreen = closeDetailScreen,
                navigateToDetail = navigateToDetail
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
        }
    }
}

@Composable
fun SampleSinglePaneContent(
    uiState: SampleUIState,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Sample, ContentType) -> Unit
) {
    if (uiState.selectedSample != null && uiState.isDetailOnlyOpen) {
        BackHandler {
            closeDetailScreen()
        }
        SampleDetail(sample = uiState.selectedSample) {
            closeDetailScreen()
        }
    } else {
        SampleList(
            modifier = modifier,
            uiState = uiState,
            lazyListState = lazyListState,
            contentType = ContentType.SINGLE_PANE,
            navigateToDetail = navigateToDetail,
        )
    }
}

@Composable
fun SampleDualPaneContent(
    modifier: Modifier = Modifier,
    uiState: SampleUIState,
    lazyListState: LazyListState,
    navigateToDetail: (Sample, ContentType) -> Unit
) {
    Row(modifier = modifier) {
        SampleList(
            uiState = uiState,
            lazyListState = lazyListState,
            contentType = ContentType.DUAL_PANE,
            navigateToDetail = navigateToDetail
        )

        SampleDetail(
            modifier = Modifier.weight(1f),
            isFullScreen = false,
            sample = uiState.selectedSample ?: uiState.group.values.flatten().first()
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SampleList(
    modifier: Modifier = Modifier,
    uiState: SampleUIState,
    lazyListState: LazyListState,
    contentType: ContentType,
    navigateToDetail: (Sample, ContentType) -> Unit
) {
    val grouped = uiState.group
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
                    SampleListItem(sample = sample) { sample ->
                        navigateToDetail(sample, contentType)
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
