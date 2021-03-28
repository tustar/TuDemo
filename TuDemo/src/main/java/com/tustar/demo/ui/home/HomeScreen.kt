package com.tustar.demo.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tustar.demo.R
import com.tustar.demo.data.model.DemoItem
import com.tustar.demo.data.remote.Now
import com.tustar.demo.ui.theme.sectionBgColor
import com.tustar.demo.ui.theme.sectionTextColor
import com.tustar.demo.ui.theme.typography

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    now: Now?
) {
    val viewModel: HomeViewModel = viewModel()
    val grouped = viewModel.createDemos()

    Column(modifier = modifier) {
        HomeTopAppBar(
            modifier = modifier,
            now = now
        )
        HomeListContent(
            modifier = modifier,
            grouped = grouped
        )
    }
}

@Composable
private fun HomeTopAppBar(
    modifier: Modifier,
    now: Now?
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.title_home))
        },
        actions = {
            WeatherContent(now)
        },
        modifier = modifier
            .height(52.dp),
        backgroundColor = MaterialTheme.colors.primary,
    )
}

@Composable
fun WeatherContent(now: Now?) {
    now?.let {
        Column() {
            Text(text = now.text)
            Text(text = stringResource(id = R.string.weather_temp, now.temp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeListContent(
    modifier: Modifier,
    grouped: Map<Int, List<DemoItem>>
) {

    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = modifier,
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
                DemoItemRow(demos[it])
            }
        }
    }
}

@Composable
fun DemoHeader(groupResId: Int) {
    Text(
        text = stringResource(id = groupResId),
        modifier = Modifier
            .background(sectionBgColor)
            .padding(start = 16.dp, top = 2.dp, bottom = 2.dp)
            .fillMaxWidth(),
        style = typography.subtitle2,
        color = sectionTextColor,
    )
}

@Composable
fun DemoItemRow(demoItem: DemoItem) {
    ConstraintLayout(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp)
            .wrapContentHeight()
    ) {
        val (title, createdAtIcon, createdAt, updatedAtIcon, updatedAt) = createRefs()
        Text(
            text = stringResource(id = demoItem.item),
            modifier = Modifier
                .padding(start = 16.dp, top = 0.dp, bottom = 4.dp, end = 16.dp)
                .fillMaxWidth()
                .constrainAs(title) {
                },
            style = typography.h6,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_created_at),
            contentDescription = null,
            modifier = Modifier
                .size(10.dp)
                .constrainAs(createdAtIcon) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start, 16.dp)
                    bottom.linkTo(createdAt.bottom)
                },
        )

        Text(
            text = demoItem.createdAt,
            modifier = Modifier
                .constrainAs(createdAt) {
                    top.linkTo(title.bottom)
                    start.linkTo(createdAtIcon.end, 4.dp)
                },
            style = typography.body2,
            fontSize = 12.sp
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_updated_at),
            contentDescription = null,
            modifier = Modifier
                .size(10.dp)
                .constrainAs(updatedAtIcon) {
                    top.linkTo(title.bottom)
                    end.linkTo(updatedAt.start, 4.dp)
                    bottom.linkTo(updatedAt.bottom)
                }
        )
        Text(
            text = demoItem.updatedAt,
            modifier = Modifier
                .constrainAs(updatedAt) {
                    top.linkTo(title.bottom)
                    end.linkTo(parent.end, 16.dp)
                },
            style = typography.body2,
            fontSize = 12.sp
        )
    }
}