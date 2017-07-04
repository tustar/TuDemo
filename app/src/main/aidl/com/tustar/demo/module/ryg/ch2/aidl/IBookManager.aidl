// IBookManager.aidl
package com.tustar.demo.module.ryg.ch2.aidl;

import com.tustar.demo.module.ryg.ch2.aidl.Book;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
