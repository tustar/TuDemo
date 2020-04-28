package com.tustar.demo.ui.jet.pagingroom

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

class BookViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BookViewModel(context) as T
    }
}

class BookViewModel(private val context: Context) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 30
        private const val ENABLE_PLACEHOLDERS = true
    }

    val dao = BookDb.get(context).bookDao()
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
