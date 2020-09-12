package com.tustar.demo.ui.article

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tustar.demo.data.model.Article
import java.util.*
import kotlin.collections.ArrayList

class ArticleViewModel @ViewModelInject constructor() : ViewModel() {

    val articles get() = _articles
    private val _articles = MutableLiveData<List<Article>>().apply {
        val article = Article(
            "给Android开发者的RxJava详解",
            "我从去年开始使用 RxJava ，到现在一年多了。今年加入了 Flipboard 后，看到" +
                    " Flipboard 的 Android 项目也在使用 RxJava ，并且使用的场景越来越多 。而最近这几个月" +
                    "，我也发现国内越来越多的人开始提及 RxJava 。",
            url = "https://gank.io/post/560e15be2dca930e00da1083",
            author = "扔物线",
            createAt = System.currentTimeMillis()
        )
        val articles = ArrayList<Article>(Collections.nCopies(1, article))
        postValue(articles)
    }
}