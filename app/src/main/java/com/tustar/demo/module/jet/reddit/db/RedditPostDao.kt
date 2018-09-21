package com.tustar.demo.module.jet.reddit.db

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.tustar.demo.module.jet.reddit.vo.RedditPost

@Dao
interface RedditPostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<RedditPost>)

    @Query("select * from posts where subreddit=:subreddit order by indexInResponse asc")
    fun postsBySubreddit(subreddit: String): DataSource.Factory<Int, RedditPost>

    @Query("delete from posts where subreddit=:subreddit")
    fun deleteBySubreddit(subreddit: String)

    @Query("select max(indexInResponse)+1 from posts where subreddit:=subreddit")
    fun getNextInSubreddit(subreddit: String): Int
}