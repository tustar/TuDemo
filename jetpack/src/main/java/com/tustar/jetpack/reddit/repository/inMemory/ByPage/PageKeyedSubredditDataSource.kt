package com.tustar.jetpack.reddit.repository.inMemory.ByPage

import androidx.paging.ItemKeyedDataSource
import androidx.paging.PageKeyedDataSource
import com.tustar.jetpack.reddit.api.RedditApi
import com.tustar.jetpack.reddit.vo.RedditPost
import java.util.concurrent.Executor

class PageKeyedSubredditDataSource(
        private val redditApi: RedditApi,
        private val subredditName: String,
        private val retryExecutor: Executor) : PageKeyedDataSource<String, RedditPost>() {

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, RedditPost>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}