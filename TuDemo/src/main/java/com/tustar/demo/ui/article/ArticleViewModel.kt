package com.tustar.demo.ui.article

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tustar.demo.data.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class ArticleViewModel @Inject constructor() : ViewModel() {

    val articles get() = _articles
    private val _articles = MutableLiveData<List<Article>>().apply {
        val article = Article(
            id = 1,
            title = "给Android开发者的RxJava详解",
            description = "我从去年开始使用 RxJava ，到现在一年多了。今年加入了 Flipboard 后，看到" +
                    " Flipboard 的 Android 项目也在使用 RxJava ，并且使用的场景越来越多 。而最近这几个月" +
                    "，我也发现国内越来越多的人开始提及 RxJava 。",
            url = "https://gank.io/post/560e15be2dca930e00da1083",
            author = "扔物线",
            createdAt = System.currentTimeMillis()
        )
        val articles = ArrayList<Article>(Collections.nCopies(1, article))
        postValue(articles)
    }

    fun getArticles(): Map<String, List<Article>> {
        val articles = mutableListOf<Article>()
        val article1 = Article(
            id = 1,
            title = "给Android开发者的RxJava详解",
            description = "我从去年开始使用 RxJava ，到现在一年多了。今年加入了 Flipboard 后，看到" +
                    " Flipboard 的 Android 项目也在使用 RxJava ，并且使用的场景越来越多 。而最近这几个月" +
                    "，我也发现国内越来越多的人开始提及 RxJava 。",
            url = "https://gank.io/post/560e15be2dca930e00da1083",
            author = "扔物线(朱凯)",
            createdAt = System.currentTimeMillis()
        )
        articles.add(article1)
        val article2 = Article(
            id = 2,
            title = "Android消息机制1-Handler(Java层)",
            description = "本文基于Android 6.0的源代码，来分析Java层的handler消息处理机制",
            url = "http://gityuan.com/2015/12/26/handler-message-framework/",
            author = "袁辉辉",
            createdAt = System.currentTimeMillis()
        )
        articles.add(article2)
        val article3 = Article(
            id = 3,
            title = "Android消息机制2-Handler(Native层)",
            description = "本文基于Android 6.0的源代码，来分析native层的handler消息处理机制",
            url = "http://gityuan.com/2015/12/27/handler-message-native/",
            author = "袁辉辉",
            createdAt = System.currentTimeMillis()
        )
        articles.add(article3)
        val article4 = Article(
            id = 4,
            title = "Android消息机制2-Handler(Native层)",
            description = "本文基于Android 6.0的源代码，来分析native层的handler消息处理机制",
            url = "http://gityuan.com/2015/12/27/handler-message-native/",
            author = "郭霖",
            createdAt = System.currentTimeMillis()
        )
        articles.add(article4)
        val article5 = Article(
            id = 5,
            title = "Android消息机制2-Handler(Native层)",
            description = "本文基于Android 6.0的源代码，来分析native层的handler消息处理机制",
            url = "http://gityuan.com/2015/12/27/handler-message-native/",
            author = "任玉刚",
            createdAt = System.currentTimeMillis()
        )
        articles.add(article5)
        return articles.groupBy { it.author }.toSortedMap()
    }
}