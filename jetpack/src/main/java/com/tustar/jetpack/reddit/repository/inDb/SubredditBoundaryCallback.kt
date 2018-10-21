package com.tustar.jetpack.reddit.repository.inDb

import androidx.paging.PagedList
import com.tustar.jetpack.reddit.api.RedditApi
import com.tustar.jetpack.reddit.vo.RedditPost
import java.util.concurrent.Executor

class SubredditBoundaryCallback(
        private val subredditName: String,
        private val webservice: RedditApi,
        private val handleResponse: (String, RedditApi.ListingResponse?) -> Unit,
        private val ioExecutor: Executor,
        private val networkPageSize: Int)
    : PagedList.BoundaryCallback<RedditPost>() {

    }