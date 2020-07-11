package com.tustar.demo.data

class DemoRepository private constructor(private val demoDao: DemoDao) {

    suspend fun getDemosByGroupId(groupId: Int) = demoDao.getDemosByGroupId(groupId)
    suspend fun getDemosByParentId(parentId: Int) = demoDao.getDemosByParentId(parentId)

    companion object {
        @Volatile
        private var instance: DemoRepository? = null

        fun getInstance(demoDao: DemoDao): DemoRepository {
            return instance ?: synchronized(this) {
                instance ?: DemoRepository(demoDao).also {
                    instance = it
                }
            }
        }
    }
}