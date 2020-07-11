package com.tustar.demo.data

import androidx.room.*

@Dao
interface DemoDao {

    @Query("SELECT * FROM demos WHERE group_id = :groupId AND parent_id=-1 ORDER BY created_at")
    suspend fun getDemosByGroupId(groupId: Int): List<Demo>

    @Query("SELECT * FROM demos WHERE parent_id = :parentId ORDER BY created_at")
    suspend fun getDemosByParentId(parentId: Int): List<Demo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDemo(demo: Demo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(demos: List<Demo>)

    @Update
    suspend fun updateDemo(demo: Demo)

    @Delete
    suspend fun deleteDemo(demo: Demo)
}