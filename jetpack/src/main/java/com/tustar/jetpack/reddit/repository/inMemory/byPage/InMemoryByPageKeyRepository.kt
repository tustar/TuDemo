package com.tustar.jetpack.reddit.repository.inMemory.byPage

import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import com.tustar.jetpack.reddit.api.RedditApi
import com.tustar.jetpack.reddit.repository.Listing
import com.tustar.jetpack.reddit.repository.RedditPostRepository
import com.tustar.jetpack.reddit.vo.RedditPost
import java.util.concurrent.Executor

class InMemoryByPageKeyRepository(private val redditApi: RedditApi,
                                  private val networkExecutor: Executor) : RedditPostRepository {

    override fun postsOfSubreddit(subReddit: String, pageSize: Int): Listing<RedditPost> {
        val sourceFactory = SubRedditDataSourceFactory(redditApi, subReddit, networkExecutor)

        val livePagedList = LivePagedListBuilder(sourceFactory, pageSize)
                // provide custom executor for network requests, otherwise it will default to
                // Arch Components' IO pool which is also used for disk access
                .setFetchExecutor(networkExecutor)
                .build()

        val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
            it.initialLoad
        }
        return Listing(
                pagedList = livePagedList,
                networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                    it.networkState
                },
                retry = {
                    sourceFactory.sourceLiveData.value?.retryAllFailed()
                },
                refresh = {
                    sourceFactory.sourceLiveData.value?.invalidate()
                },
                refreshState = refreshState
        )
    }
}