package com.tustar.demo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.tustar.demo.R
import com.tustar.demo.data.remote.Now
import com.tustar.demo.ui.article.ArticleContent
import com.tustar.demo.ui.home.HomeContent
import com.tustar.demo.ui.me.MeContent
import com.tustar.demo.ui.theme.DemoTheme
import com.tustar.demo.ui.todo.TodoContent

// Start building your app here!
@Composable
fun MainScreen(now: Now? = null) {
    DemoTheme {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize(),
        ) {

            val state = remember { mutableStateOf(R.string.title_home) }
            val (content, tabRow) = createRefs()

            MainContent(
                now = now,
                state = state,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
                    .constrainAs(content)
                    {
                        linkTo(
                            top = parent.top,
                            bottom = tabRow.top,
                        )
                        height = Dimension.fillToConstraints
                    })

            MainBottomTabBar(
                state = state,
                modifier = Modifier.constrainAs(tabRow) {
                    bottom.linkTo(parent.bottom)
                })
        }
    }
}

@Composable
fun MainContent(
    now: Now?,
    state: MutableState<Int>,
    modifier: Modifier,
) {
    when (state.value) {
        R.string.title_home -> HomeContent(modifier = modifier, now = now)
        R.string.title_article -> ArticleContent(modifier = modifier)
        R.string.title_todo -> TodoContent(modifier = modifier)
        R.string.title_me -> MeContent(modifier = modifier)
    }
}

@Composable
fun MainBottomTabBar(
    state: MutableState<Int>,
    modifier: Modifier = Modifier
) {

    val titles = mapOf(
        R.string.title_home to R.drawable.ic_tab_home,
        R.string.title_article to R.drawable.ic_tab_article,
        R.string.title_todo to R.drawable.ic_tab_todo,
        R.string.title_me to R.drawable.ic_tab_me,
    )

    TabRow(
        selectedTabIndex = state.value,
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.primary,
        indicator = {},
    ) {
        titles.mapKeys { (title, icon) ->
            Tab(
                text = {
                    Text(
                        text = stringResource(id = title)
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = stringResource(id = title),
                    )
                },
                selected = state.value == title,
                onClick = {
                    state.value = title
                },
            )
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    DemoTheme {
        MainScreen()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    DemoTheme(darkTheme = true) {
        MainScreen()
    }
}