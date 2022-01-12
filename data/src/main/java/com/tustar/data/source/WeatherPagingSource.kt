package com.tustar.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tustar.data.Weather
import com.tustar.data.source.remote.HeService
import javax.inject.Inject

class WeatherPagingSource @Inject constructor(
    private val repo: WeatherRepository,
    locations: List<String>
) : PagingSource<Int, Weather>() {

    override fun getRefreshKey(state: PagingState<Int, Weather>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Weather> {
        val page = params.key ?: 1
        val weather =
    }
}