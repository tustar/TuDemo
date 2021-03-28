package com.tustar.demo.ui.article

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoteAdd
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
import com.tustar.demo.data.model.Article
import com.tustar.demo.ex.toFormatDate
import com.tustar.demo.ui.theme.sectionBgColor
import com.tustar.demo.ui.theme.sectionTextColor
import com.tustar.demo.ui.theme.typography

@Composable
fun ArticleContent(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ArticleTopAppBar(modifier = modifier)
        ArticleTabsContent(modifier = modifier)
    }
}

@Composable
private fun ArticleTopAppBar(
    modifier: Modifier
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.title_article))
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.NoteAdd,
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
fun ArticleTabsContent(
    modifier: Modifier
) {

    val viewModel: ArticleViewModel = viewModel()
    val grouped = viewModel.getArticles()
    val keys = grouped.keys.toList()
    val state = remember { mutableStateOf(0) }

    Column(modifier = modifier) {
        ScrollableTabRow(selectedTabIndex = state.value) {
            grouped.keys.forEach { tabIndex ->
                Tab(
                    text = {
                        Text(text = tabIndex)
                    },
                    selected = state.value == keys.indexOf(tabIndex),
                    onClick = {
                        state.value = keys.indexOf(tabIndex)
                    },
                )
            }
        }
        val subGrouped = grouped[keys[state.value]]!!.groupBy { it.title }
        ArticleListContent(modifier = modifier, subGrouped = subGrouped)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArticleListContent(
    modifier: Modifier,
    subGrouped: Map<String, List<Article>>
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        subGrouped.forEach { (author, articles) ->
            stickyHeader {
                ArticleHeader(author)
            }

            items(count = articles.size,
                key = {
                    articles[it].id
                }) {
                ArticleItemRow(articles[it])
            }
        }
    }
}

@Composable
fun ArticleHeader(group: String) {
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
fun ArticleItemRow(article: Article) {
    ConstraintLayout(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp)
            .wrapContentHeight()
    ) {
        val (title, createdAtIcon, description, createdAt, authorIcon, author) = createRefs()
        Text(
            text = article.title,
            modifier = Modifier
                .padding(start = 16.dp, top = 0.dp, bottom = 4.dp, end = 16.dp)
                .fillMaxWidth()
                .constrainAs(title) {
                },
            style = typography.h6,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(
            text = article.description,
            modifier = Modifier
                .padding(start = 16.dp, top = 0.dp, bottom = 4.dp, end = 16.dp)
                .fillMaxWidth()
                .constrainAs(description) {
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
                    start.linkTo(parent.start, 16.dp)
                    bottom.linkTo(createdAt.bottom)
                },
        )

        Text(
            text = article.createdAt.toFormatDate(),
            modifier = Modifier
                .constrainAs(createdAt) {
                    top.linkTo(description.bottom)
                    start.linkTo(createdAtIcon.end, 4.dp)
                },
            style = typography.body2,
            fontSize = 12.sp
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_author),
            contentDescription = null,
            modifier = Modifier
                .size(10.dp)
                .constrainAs(authorIcon) {
                    top.linkTo(description.bottom)
                    end.linkTo(author.start, 4.dp)
                    bottom.linkTo(author.bottom)
                }
        )
        Text(
            text = article.author,
            modifier = Modifier
                .constrainAs(author) {
                    top.linkTo(description.bottom)
                    end.linkTo(parent.end, 16.dp)
                },
            style = typography.body2,
            fontSize = 12.sp
        )
    }
}