package com.tustar.demo.ui.article

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.NoteAdd
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
        ArticleListContent(modifier = modifier)
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArticleListContent(
    modifier: Modifier
) {

    val viewModel: ArticleViewModel = viewModel()
    val grouped = viewModel.getArticles()

    val listState = rememberLazyListState()


    LazyColumn(
        state = listState,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        grouped.forEach { (author, articles) ->
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
fun ArticleHeader(author: String) {
    Text(
        text = author,
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