package com.tustar.demo.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
    val id: Long,
    val title: String,
    val desc: String,
    val category: String,
    val url: String,
    var img: String? = null,
    val author: String,
    val createdAt: Long
) : Parcelable

val articles = listOf(
    Article(
        id = 1,
        title = "给Android开发者的RxJava详解",
        desc = "我从去年开始使用 RxJava ，到现在一年多了。今年加入了 Flipboard 后，看到" +
                " Flipboard 的 Android 项目也在使用 RxJava ，并且使用的场景越来越多 。而最近这几个月" +
                "，我也发现国内越来越多的人开始提及 RxJava 。",
        category = "干货",
        url = "https://gank.io/post/560e15be2dca930e00da1083",
        author = "扔物线(朱凯)",
        createdAt = System.currentTimeMillis()
    ),
    Article(
        id = 2,
        title = "给Android开发者的RxJava详解",
        desc = "我从去年开始使用 RxJava ，到现在一年多了。今年加入了 Flipboard 后，看到" +
                " Flipboard 的 Android 项目也在使用 RxJava ，并且使用的场景越来越多 。而最近这几个月" +
                "，我也发现国内越来越多的人开始提及 RxJava 。",
        category = "干货",
        url = "https://gank.io/post/560e15be2dca930e00da1083",
        author = "扔物线(朱凯)",
        createdAt = System.currentTimeMillis()
    ),
    Article(
        id = 3,
        title = "给Android开发者的RxJava详解",
        desc = "我从去年开始使用 RxJava ，到现在一年多了。今年加入了 Flipboard 后，看到" +
                " Flipboard 的 Android 项目也在使用 RxJava ，并且使用的场景越来越多 。而最近这几个月" +
                "，我也发现国内越来越多的人开始提及 RxJava 。",
        category = "干货",
        url = "https://gank.io/post/560e15be2dca930e00da1083",
        author = "扔物线(朱凯)",
        createdAt = System.currentTimeMillis()
    ),
)

object ArticleRepo {
    fun getCourse(articleId: Long): Article = articles.find { it.id == articleId }!!
    fun getRelated(@Suppress("UNUSED_PARAMETER") articleId: Long): List<Article> = articles
}