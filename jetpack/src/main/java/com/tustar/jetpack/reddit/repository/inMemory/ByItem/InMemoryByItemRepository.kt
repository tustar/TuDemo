package com.tustar.jetpack.reddit.repository.inMemory.ByItem

import com.tustar.jetpack.reddit.api.RedditApi
import com.tustar.jetpack.reddit.repository.Listing
import com.tustar.jetpack.reddit.repository.RedditPostRepository
import com.tustar.jetpack.reddit.vo.RedditPost
import java.util.concurrent.Executor

class InMemoryByItemRepository(
        private val redditApi: RedditApi,
        private val networkExecutor: Executor) : RedditPostRepository {
    override fun postsOfSubreddit(subReddit: String, pageSize: Int): Listing<RedditPost> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}