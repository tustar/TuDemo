package com.tustar.demo.data

class GroupRepository private constructor(private val groupDao: GroupDao) {

    suspend fun getGroups() = groupDao.getGroups()

    companion object {
        @Volatile
        private var instance: GroupRepository? = null

        fun getInstance(groupDao: GroupDao): GroupRepository {
            return instance ?: synchronized(this) {
                instance ?: GroupRepository(groupDao).also {
                    instance = it
                }
            }
        }
    }
}