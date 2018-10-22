package com.tustar.jetpack.reddit.repository.inMemory.byItem

import androidx.annotation.MainThread
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.tustar.jetpack.reddit.api.RedditApi
import com.tustar.jetpack.reddit.repository.Listing
import com.tustar.jetpack.reddit.repository.RedditPostRepository
import com.tustar.jetpack.reddit.vo.RedditPost
import java.util.concurrent.Executor

class InMemoryByItemRepository(
        private val redditApi: RedditApi,
        private val networkExecutor: Executor) : RedditPostRepository {

    @MainThread
    override fun postsOfSubreddit(subReddit: String, pageSize: Int): Listing<RedditPost> {
        val sourceFactory = SubRedditDataSourceFactory(redditApi, subReddit, networkExecutor)
        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(pageSize * 2)
                .setPageSize(pageSize)
                .build()
        val pagedList = LivePagedListBuilder(sourceFactory, pagedListConfig)
                .setFetchExecutor(networkExecutor)
                .build()
        val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
            it.initialLoad
        }
        return Listing(
                pagedList=pagedList,
                networkState = Transformations.switchMap(sourceFactory.sourceLiveData){
                    it.networkState
                },
                retry = {
                    sourceFactory.sourceLiveData.value?.retryAllFailed()
                }
                ,
                refresh = {
                    sourceFactory.sourceLiveData.value?.invalidate()
                },
                refreshState = refreshState
        )
    }
}