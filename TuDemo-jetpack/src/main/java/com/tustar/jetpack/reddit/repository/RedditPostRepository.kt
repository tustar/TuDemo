package com.tustar.jetpack.reddit.repository

import com.tustar.jetpack.reddit.vo.RedditPost

interface RedditPostRepository{

    fun postsOfSubreddit(subReddit:String, pageSize:Int):Listing<RedditPost>

    enum class Type {
        IN_MEMORY_BY_ITEM,
        IN_MEMORY_BY_PAGE,
        DB
    }
}