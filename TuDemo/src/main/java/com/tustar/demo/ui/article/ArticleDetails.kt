package com.tustar.demo.ui.article

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tustar.demo.R
import com.tustar.demo.data.model.Article
import com.tustar.demo.data.model.ArticleRepo
import com.tustar.demo.ex.topAppBar
import com.tustar.demo.ui.theme.DemoTheme

@Composable
fun ArticleDetails(
    articleId: Long,
    selectArticle: (Long) -> Unit,
    upPress: () -> Unit
) {
    val article = remember { ArticleRepo.getCourse(articleId) }
    // TODO: Show error if course not found.
    ArticleDetails(article, selectArticle, upPress)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticleDetails(
    article: Article,
    selectArticle: (Long) -> Unit,
    upPress: () -> Unit
) {
    DemoTheme {
        BoxWithConstraints {
            ArticleDescription(article, selectArticle, upPress)
        }
    }
}

@Composable
fun ArticleDescription(
    article: Article,
    selectArticle: (Long) -> Unit,
    upPress: () -> Unit
) {
    LazyColumn {
        item { ArticleDescriptionHeader(article, upPress) }
        item { ArticleDescriptionBody(article) }
    }
}

@Composable
fun ArticleDescriptionHeader(
    article: Article,
    upPress: () -> Unit
) {
    Box {
        TopAppBar(
            elevation = 0.dp,
            contentColor = Color.White, // always white as image has dark scrim
            modifier = Modifier.topAppBar(),
        ) {
            IconButton(onClick = upPress) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun ArticleDescriptionBody(article: Article) {
    Text(
        text = article.title,
        style = MaterialTheme.typography.h4,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            text = article.desc,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}
