package com.tustar.demo.module.jet.reddit.repository.inDb

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread
import com.tustar.demo.module.jet.reddit.api.RedditApi
import com.tustar.demo.module.jet.reddit.db.RedditDb
import com.tustar.demo.module.jet.reddit.repository.Listing
import com.tustar.demo.module.jet.reddit.repository.NetworkState
import com.tustar.demo.module.jet.reddit.repository.RedditPostRepository
import com.tustar.demo.module.jet.reddit.vo.RedditPost
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

    private fun insertResultIntoDb(subredditName: String, body: RedditApi.ListingResponse?) {
        body!!.data.children.let { posts ->
            db.runInTransaction {
                val start = db.posts().getNextInSubreddit(subredditName)
                val items = posts.mapIndexed { index, child ->
                    child.data.indexInResponse = start + index
                    child.data
                }
                db.posts().insert(items)
            }
        }
    }

    @MainThread
    private fun refresh(subredditName: String):LiveData<NetworkState> {
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
                            networkState.postValue(NetworkState.LOADED)
                        }
                    }
                }
        )
        return networkState
    }

    override fun postsOfSubreddit(subreddit: String, pageSize: Int): Listing<RedditPost> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}