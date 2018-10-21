package com.tustar.jetpack.reddit.repository.inMemory.ByItem

import androidx.paging.ItemKeyedDataSource
import com.tustar.jetpack.reddit.api.RedditApi
import com.tustar.jetpack.reddit.vo.RedditPost
import java.util.concurrent.Executor

class ItemKeyedSubredditDataSource(
        private val redditApi: RedditApi,
        private val subredditName: String,
        private val retryExecutor: Executor)
    : ItemKeyedDataSource<String, RedditPost>() {

    override fun getKey(item: RedditPost): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<RedditPost>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<RedditPost>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<RedditPost>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}