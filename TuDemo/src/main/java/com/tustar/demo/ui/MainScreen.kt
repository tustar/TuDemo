package com.tustar.demo.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.navigationBarsPadding
import com.tustar.demo.R
import com.tustar.demo.data.model.articles
import com.tustar.demo.ui.article.ArticleContent
import com.tustar.demo.ui.home.HomeContent
import com.tustar.demo.ui.me.MeContent
import com.tustar.demo.ui.todo.TodoContent

// Start building your app here!
@Composable
fun MainContent(selectArticle: (Long) -> Unit) {
    val (selectedTab, setSelectedTab) = remember { mutableStateOf(MainTabs.Article) }
    val tabs = MainTabs.values()
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = {
            BottomNavigation(
                Modifier.navigationBarsHeight(additional = 56.dp)
            ) {
                tabs.forEach { tab ->
                    BottomNavigationItem(
                        icon = { Icon(painterResource(tab.icon), contentDescription = null) },
                        label = { Text(stringResource(tab.title).toUpperCase()) },
                        selected = tab == selectedTab,
                        onClick = { setSelectedTab(tab) },
                        alwaysShowLabel = true,
                        selectedContentColor = LocalContentColor.current,
                        unselectedContentColor = MaterialTheme.colors.secondary,
                        modifier = Modifier.navigationBarsPadding()
                    )
                }
            }
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        when (selectedTab) {
            MainTabs.HOME -> HomeContent(modifier)
            MainTabs.Article -> ArticleContent(articles, selectArticle, modifier)
            MainTabs.Todo -> TodoContent(modifier)
            MainTabs.Me -> MeContent(modifier)
        }
    }
}

private enum class MainTabs(
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    HOME(R.string.title_home, R.drawable.ic_tab_home),
    Article(R.string.title_article, R.drawable.ic_tab_article),
    Todo(R.string.title_todo, R.drawable.ic_tab_todo),
    Me(R.string.title_me, R.drawable.ic_tab_me),
}
