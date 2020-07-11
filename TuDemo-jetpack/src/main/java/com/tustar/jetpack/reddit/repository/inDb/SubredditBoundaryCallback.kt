package com.tustar.jetpack.reddit.repository.inDb

import androidx.annotation.MainThread
import androidx.paging.PagedList
import com.androidx.paging.PagingRequestHelper
import com.tustar.jetpack.reddit.api.RedditApi
import com.tustar.jetpack.reddit.util.createStatusLiveData
import com.tustar.jetpack.reddit.vo.RedditPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class SubredditBoundaryCallback(
        private val subredditName: String,
        private val webservice: RedditApi,
        private val handleResponse: (String, RedditApi.ListingResponse?) -> Unit,
        private val ioExecutor: Executor,
        private val networkPageSize: Int)
    : PagedList.BoundaryCallback<RedditPost>() {

    val helper = PagingRequestHelper(ioExecutor)
    val networkState = helper.createStatusLiveData()

    @MainThread
    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            webservice.getTop(
                    subreddit = subredditName,
                    limit = networkPageSize).enqueue(
                    createWebserviceCallback(it))
        }
    }

    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: RedditPost) {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            webservice.getTopAfter(
                    subreddit = subredditName,
                    after = itemAtEnd.name,
                    limit = networkPageSize)
                    .enqueue(createWebserviceCallback(it))
        }
    }

    override fun onItemAtFrontLoaded(itemAtFront: RedditPost) {
        // ignored
    }

    private fun insertItemsIntoDb(response: Response<RedditApi.ListingResponse>,
                                  it: PagingRequestHelper.Request.Callback) {
        ioExecutor.execute {
            handleResponse(subredditName, response.body())
            it.recordSuccess()
        }
    }


    private fun createWebserviceCallback(it: PagingRequestHelper.Request.Callback)
            : Callback<RedditApi.ListingResponse> {
        return object : Callback<RedditApi.ListingResponse> {
            override fun onFailure(call: Call<RedditApi.ListingResponse>, t: Throwable) {
                it.recordFailure(t)
            }

            override fun onResponse(call: Call<RedditApi.ListingResponse>,
                                    response: Response<RedditApi.ListingResponse>) {
                insertItemsIntoDb(response, it)
            }
        }
    }
}