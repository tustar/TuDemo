package com.tustar.demo.module.jet.reddit.repository

import com.tustar.demo.module.jet.reddit.vo.RedditPost

interface RedditPostRepository {

    fun postsOfSubreddit(subreddit: String, pageSize: Int): Listing<RedditPost>

    enum class TYPE {
        IN_MEMORY_BY_ITEM,
        IN_MEMORY_BY_PAGE,
        DB
    }
}