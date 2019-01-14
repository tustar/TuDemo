package com.tustar.demo.ui.jet.pagingroom

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

class BookViewModel(app: Application) : AndroidViewModel(app) {

    companion object {
        private const val PAGE_SIZE = 30
        private const val ENABLE_PLACEHOLDERS = true
    }

    val dao = BookDb.get(app).bookDao()
    val allBooks = LivePagedListBuilder(dao.allBooksByName(),
            PagedList.Config.Builder()
                    .setPageSize(PAGE_SIZE)
                    .setEnablePlaceholders(ENABLE_PLACEHOLDERS)
                    .build()).build()

    fun insert(text: CharSequence) = ioThread {
        dao.insert(Book(id = 0, name = text.toString()))
    }

    fun remove(book: Book) = ioThread {
        dao.delete(book)
    }
}
