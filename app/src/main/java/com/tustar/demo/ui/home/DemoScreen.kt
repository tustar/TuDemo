package com.tustar.demo.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tustar.demo.R
import com.tustar.demo.data.model.DemoItem
import com.tustar.demo.ex.topAppBar
import com.tustar.demo.ui.custom.SvgChinaScreen
import com.tustar.demo.ui.custom.WaveViewScreen
import com.tustar.demo.ui.theme.sectionBgColor
import com.tustar.demo.ui.theme.sectionTextColor
import com.tustar.demo.ui.theme.typography

@Composable
fun Demos(
    onDemoClick: (Int) -> Unit,
    modifier: Modifier
) {
    val viewModel: DemoViewModel = viewModel()
    val grouped = viewModel.createDemos()

    Column(modifier) {
        HomeTopAppBar()
        HomeListContent(grouped, onDemoClick)
    }
}

@Composable
fun HomeTopAppBar() {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.title_home))
        },
        modifier = Modifier.topAppBar(),
        backgroundColor = MaterialTheme.colors.primary,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeListContent(
    grouped: Map<Int, List<DemoItem>>,
    onDemoClick: (Int) -> Unit,
) {

    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        grouped.forEach { (group, demos) ->
            stickyHeader {
                DemoHeader(group)
            }

            items(count = demos.size,
                key = {
                    demos[it].item
                }) {
                DemoItemRow(demos[it], onDemoClick)
            }
        }
    }
}

@Composable
fun DemoHeader(group: Int) {
    Text(
        text = stringResource(id = group),
        modifier = Modifier
            .background(sectionBgColor)
            .padding(start = 16.dp, top = 2.dp, bottom = 2.dp)
            .fillMaxWidth(),
        style = typography.subtitle2,
        color = sectionTextColor,
    )
}

@Composable
fun DemoItemRow(
    demoItem: DemoItem,
    onDemoClick: (Int) -> Unit,
) {
    Text(
        text = stringResource(id = demoItem.item),
        modifier = Modifier
            .padding(start = 16.dp, top = 0.dp, bottom = 4.dp, end = 16.dp)
            .fillMaxWidth()
            .clickable {
                onDemoClick(demoItem.item)
            }
            .height(50.dp),
        style = typography.h6,
        overflow = TextOverflow.Ellipsis,
        maxLines = 2,
    )
}

@Composable
fun DemoDetails(demoId: Int, upPress: () -> Unit) {
    when (demoId) {
        R.string.custom_wave_view -> WaveViewScreen(demoId, upPress)
        R.string.custom_svg_china -> SvgChinaScreen(demoId, upPress)
    }
}
