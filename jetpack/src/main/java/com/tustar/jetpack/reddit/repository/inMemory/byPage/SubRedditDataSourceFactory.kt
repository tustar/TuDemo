package com.tustar.jetpack.reddit.repository.inMemory.byPage

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.tustar.jetpack.reddit.api.RedditApi
import com.tustar.jetpack.reddit.vo.RedditPost
import java.util.concurrent.Executor

class SubRedditDataSourceFactory(
        private val redditApi: RedditApi,
        private val subredditName: String,
        private val retryExecutor: Executor) : DataSource.Factory<String, RedditPost>() {

    val sourceLiveData = MutableLiveData<PageKeyedSubredditDataSource>()
    override fun create(): DataSource<String, RedditPost> {
        val source = PageKeyedSubredditDataSource(redditApi, subredditName, retryExecutor)
        sourceLiveData.postValue(source)
        return source
    }
}