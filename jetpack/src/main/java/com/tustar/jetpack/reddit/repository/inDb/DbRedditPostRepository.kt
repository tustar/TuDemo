package com.tustar.jetpack.reddit.repository.inDb

import com.tustar.jetpack.reddit.api.RedditApi
import com.tustar.jetpack.reddit.db.RedditDb
import com.tustar.jetpack.reddit.repository.Listing
import com.tustar.jetpack.reddit.repository.RedditPostRepository
import com.tustar.jetpack.reddit.vo.RedditPost
import java.util.concurrent.Executor

class DbRedditPostRepository(
        val db: RedditDb,
        private val redditApi: RedditApi,
        private val ioExecutor: Executor,
        private val networkPageSize: Int = DEFAULT_NETWORK_PAGE_SIZE) : RedditPostRepository {
    companion object {
        private const val DEFAULT_NETWORK_PAGE_SIZE = 10
    }

    override fun postsOfSubreddit(subReddit: String, pageSize: Int): Listing<RedditPost> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}