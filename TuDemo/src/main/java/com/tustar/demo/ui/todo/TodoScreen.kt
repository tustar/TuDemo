package com.tustar.demo.ui.todo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tustar.demo.R
import com.tustar.demo.ex.toFormatDate
import com.tustar.demo.ui.theme.sectionBgColor
import com.tustar.demo.ui.theme.sectionTextColor
import com.tustar.demo.ui.theme.typography

@Composable
fun TodoContent(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        TodoTopAppBar(modifier = modifier)
        TodoTabsContent(modifier = modifier)
    }
}

@Composable
private fun TodoTopAppBar(
    modifier: Modifier
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.title_home))
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.AddTask,
                    contentDescription = null
                )
            }
        },
        modifier = modifier
            .height(52.dp),
        backgroundColor = MaterialTheme.colors.primary,
    )
}

@Composable
fun TodoTabsContent(
    modifier: Modifier
) {

    val viewModel: TodoViewModel = viewModel()
    val grouped = viewModel.getTodos()
    val keys = grouped.keys.toList()
    val state = remember { mutableStateOf(STATE_UNDO) }

    Column(modifier = modifier) {
        ScrollableTabRow(selectedTabIndex = state.value) {
            grouped.keys.forEach { tabIndex ->
                Tab(
                    text = {
                        Text(
                            text = stringResource(
                                id = stateToStringResId(tabIndex)
                            )
                        )
                    },
                    selected = state.value == tabIndex,
                    onClick = {
                        state.value = tabIndex
                    },
                )
            }
        }
        val subGrouped = grouped[keys[state.value]]!!.groupBy { it.category }
        TodoListContent(modifier = modifier, subGrouped = subGrouped)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoListContent(
    modifier: Modifier,
    subGrouped: Map<String, List<Todo>>
) {

    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        subGrouped.forEach { (group, todos) ->
            stickyHeader {
                TodoHeader(group)
            }

            items(count = todos.size,
                key = {
                    todos[it].id
                }) {
                TodoItemRow(todos[it])
            }
        }
    }
}

@Composable
fun TodoHeader(group: String) {
    Text(
        text = group,
        modifier = Modifier
            .background(sectionBgColor)
            .padding(start = 16.dp, top = 2.dp, bottom = 2.dp)
            .fillMaxWidth(),
        style = typography.subtitle2,
        color = sectionTextColor,
    )
}

@Composable
fun TodoItemRow(todo: Todo) {
    ConstraintLayout(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp)
            .wrapContentHeight()
    ) {
        val (checkbox, title, createdAtIcon, description, createdAt, updatedAtIcon, updatedAt) = createRefs()

        Checkbox(checked = todo.state == STATE_DONE,
            modifier = Modifier
                .padding(start = 16.dp, top = 0.dp, bottom = 4.dp, end = 16.dp)
                .constrainAs(checkbox) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            onCheckedChange = { /*TODO*/ })

        Text(
            text = todo.title,
            modifier = Modifier
                .padding(start = 0.dp, top = 0.dp, bottom = 4.dp, end = 16.dp)
                .fillMaxWidth()
                .constrainAs(title) {
                    start.linkTo(checkbox.end, 8.dp)
                },
            style = typography.h6,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
        Text(
            text = todo.description,
            modifier = Modifier
                .padding(start = 0.dp, top = 0.dp, bottom = 4.dp, end = 16.dp)
                .fillMaxWidth()
                .constrainAs(description) {
                    start.linkTo(checkbox.end, 8.dp)
                    top.linkTo(title.bottom)
                },
            style = typography.subtitle2,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_created_at),
            contentDescription = null,
            modifier = Modifier
                .size(10.dp)
                .constrainAs(createdAtIcon) {
                    top.linkTo(description.bottom)
                    start.linkTo(checkbox.end, 8.dp)
                    bottom.linkTo(createdAt.bottom)
                },
        )

        Text(
            text = todo.createdAt.toFormatDate(),
            modifier = Modifier
                .constrainAs(createdAt) {
                    top.linkTo(description.bottom)
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
                    top.linkTo(description.bottom)
                    end.linkTo(updatedAt.start, 4.dp)
                    bottom.linkTo(updatedAt.bottom)
                }
        )
        Text(
            text = todo.updatedAt.toFormatDate(),
            modifier = Modifier
                .constrainAs(updatedAt) {
                    top.linkTo(description.bottom)
                    end.linkTo(parent.end, 16.dp)
                },
            style = typography.body2,
            fontSize = 12.sp
        )
    }
}