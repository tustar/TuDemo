package com.tustar.demo.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.tustar.demo.ui.MainDestinations.ARTICLE_DETAIL_ID_KEY
import com.tustar.demo.ui.article.ArticleDetails

/**
 * Destinations used in the ([DemoApp]).
 */
object MainDestinations {
    const val MAIN_ROUTE = "main"
    const val ARTICLES_ROUTE = "articles"
    const val ARTICLE_DETAIL_ROUTE = "article"
    const val ARTICLE_DETAIL_ID_KEY = "articleId"
}

@Composable
fun NavGraph(startDestination: String = MainDestinations.MAIN_ROUTE) {
    val navController = rememberNavController()

    val actions = remember(navController) { MainActions(navController) }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainDestinations.MAIN_ROUTE) {
            MainContent(selectArticle = actions.selectArticle)
        }
        composable(
            "${MainDestinations.ARTICLE_DETAIL_ROUTE}/{$ARTICLE_DETAIL_ID_KEY}",
            arguments = listOf(navArgument(ARTICLE_DETAIL_ID_KEY) { type = NavType.LongType })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            ArticleDetails(
                articleId = arguments.getLong(ARTICLE_DETAIL_ID_KEY),
                selectArticle = actions.selectArticle,
                upPress = actions.upPress
            )
        }
    }
}

/**
 * Models the navigation actions in the app.
 */
class MainActions(navController: NavHostController) {
    val selectArticle: (Long) -> Unit = { articleId: Long ->
        navController.navigate("${MainDestinations.ARTICLE_DETAIL_ROUTE}/$articleId")
    }
    val upPress: () -> Unit = {
        navController.navigateUp()
    }
}