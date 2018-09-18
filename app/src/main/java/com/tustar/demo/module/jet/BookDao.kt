package com.tustar.demo.module.jet

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface BookDao {

    @Query("select * from book order by name collate nocase desc")
    fun allBooksByName(): DataSource.Factory<Int, Book>

    @Insert
    fun insert(books: List<Book>)

    @Insert
    fun insert(book: Book)

    @Delete
    fun delete(book: Book)
}