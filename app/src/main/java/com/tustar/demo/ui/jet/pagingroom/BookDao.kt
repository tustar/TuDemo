package com.tustar.demo.ui.jet.pagingroom

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

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