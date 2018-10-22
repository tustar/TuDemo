package com.tustar.jetpack.reddit.repository.inDb

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import com.tustar.jetpack.reddit.api.RedditApi
import com.tustar.jetpack.reddit.db.RedditDb
import com.tustar.jetpack.reddit.repository.Listing
import com.tustar.jetpack.reddit.repository.NetworkState
import com.tustar.jetpack.reddit.repository.RedditPostRepository
import com.tustar.jetpack.reddit.vo.RedditPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class DbRedditPostRepository(
        val db: RedditDb,
        private val redditApi: RedditApi,
        private val ioExecutor: Executor,
        private val networkPageSize: Int = DEFAULT_NETWORK_PAGE_SIZE) : RedditPostRepository {
    companion object {
        private const val DEFAULT_NETWORK_PAGE_SIZE = 10
    }

    @MainThread
    private fun insertResultIntoDb(subredditName: String, body: RedditApi.ListingResponse?) {
        body!!.data.children.let { posts ->
            db.runInTransaction {
                val start = db.posts().getNextIndexInSubreddit(subredditName)
                val items = posts.mapIndexed { index, child ->
                    child.data.indexInResponse = start + index
                    child.data
                }
                db.posts().insert(items)
            }
        }
    }

    @MainThread
    private fun refresh(subredditName: String): LiveData<NetworkState> {
        val networkState = MutableLiveData<NetworkState>()
        networkState.value = NetworkState.LOADING
        redditApi.getTop(subredditName, networkPageSize).enqueue(
                object : Callback<RedditApi.ListingResponse> {
                    override fun onFailure(call: Call<RedditApi.ListingResponse>, t: Throwable) {
                        networkState.value = NetworkState.error(t.message)
                    }

                    override fun onResponse(call: Call<RedditApi.ListingResponse>,
                                            response: Response<RedditApi.ListingResponse>) {

                        ioExecutor.execute {
                            db.runInTransaction {
                                db.posts().deleteBySubreddit(subredditName)
                                insertResultIntoDb(subredditName, response.body())
                            }
                            //
                            networkState.postValue(NetworkState.LOADED)
                        }
                    }
                }
        )
        return networkState
    }

    @MainThread
    override fun postsOfSubreddit(subReddit: String, pageSize: Int): Listing<RedditPost> {
        val boundaryCallback = SubredditBoundaryCallback(
                webservice = redditApi,
                subredditName = subReddit,
                handleResponse = this::insertResultIntoDb,
                ioExecutor = ioExecutor,
                networkPageSize = networkPageSize)
        val dataSourceFactory = db.posts().postsBySubreddit(subReddit)
        val builder = LivePagedListBuilder(dataSourceFactory, pageSize)
                .setBoundaryCallback(boundaryCallback)

        val refreshTrigger = MutableLiveData<Unit>()
        val refreshState = Transformations.switchMap(refreshTrigger, {
            refresh(subReddit)
        })

        return Listing(pagedList = builder.build(),
                networkState = boundaryCallback.networkState,
                retry = {
                    boundaryCallback.helper.retryAllFailed()
                },
                refresh = {
                    refreshTrigger.value = null
                },
                refreshState = refreshState
        )
    }
}