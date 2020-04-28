package com.tustar.demo.data

import androidx.room.*

@Dao
interface GroupDao {
    @Query("SELECT * FROM groups ORDER BY created_at")
    suspend fun getGroups(): List<Group>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGroup(group: Group)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(groups: List<Group>)

    @Update
    suspend fun updateGroup(group: Group)

    @Delete
    suspend fun deleteGroup(group: Group)
}